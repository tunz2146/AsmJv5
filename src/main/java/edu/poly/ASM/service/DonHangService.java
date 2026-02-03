package edu.poly.ASM.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.poly.ASM.entity.*;
import edu.poly.ASM.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class DonHangService {
    
    private static final Logger log = LoggerFactory.getLogger(DonHangService.class);
    
    @Autowired
    private NguoiDungRepository nguoiDungRepository;
    
    @Autowired
    private GioHangRepository gioHangRepository;
    
    @Autowired
    private SanPhamRepository sanPhamRepository;
    
    @Autowired
    private GioHangService gioHangService;
    
    /**
     * TẠO ĐƠN HÀNG TỪ GIỎ HÀNG
     */
    public DonHang createOrder(String soDienThoai, String diaChiGiaoHang, 
                               String tenNguoiNhan, String sdtNguoiNhan) {
        
        log.info("Creating order for user: {}", soDienThoai);
        
        // Tìm người dùng
        NguoiDung nguoiDung = nguoiDungRepository.findBySoDienThoai(soDienThoai)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));
        
        // Lấy giỏ hàng
        List<GioHang> cartItems = gioHangRepository.findByNguoiDungId(nguoiDung.getId());
        
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Giỏ hàng trống");
        }
        
        // Tạo đơn hàng
        DonHang donHang = new DonHang();
        donHang.setNguoiDung(nguoiDung);
        donHang.setNgayDat(LocalDate.now());
        donHang.setTinhTrang("Chờ xác nhận");
        donHang.setDonViVanChuyen("GHTK");
        donHang.setPhiVanChuyen(0L); // Miễn phí ship
        donHang.setKhuyenMai(0L);
        
        // Tạo JSON thông tin giao hàng
        try {
            Map<String, String> shippingInfo = new HashMap<>();
            shippingInfo.put("diaChiGiaoHang", diaChiGiaoHang);
            shippingInfo.put("tenNguoiNhan", tenNguoiNhan);
            shippingInfo.put("sdtNguoiNhan", sdtNguoiNhan);
            
            ObjectMapper mapper = new ObjectMapper();
            donHang.setThongTinGiaoHang(mapper.writeValueAsString(shippingInfo));
        } catch (Exception e) {
            log.error("Error creating shipping info JSON", e);
            donHang.setThongTinGiaoHang("{\"error\":\"" + e.getMessage() + "\"}");
        }
        
        // Tạo chi tiết đơn hàng
        List<ChiTietDonHang> chiTietList = new ArrayList<>();
        long tongTien = 0;
        
        for (GioHang item : cartItems) {
            SanPham sanPham = item.getSanPham();
            
            // Kiểm tra tồn kho
            if (sanPham.getTonKho() < item.getSoLuong()) {
                throw new RuntimeException("Sản phẩm " + sanPham.getTenSanPham() + 
                        " không đủ số lượng trong kho");
            }
            
            // Tạo chi tiết đơn hàng
            ChiTietDonHang chiTiet = new ChiTietDonHang();
            chiTiet.setDonHang(donHang);
            chiTiet.setSanPham(sanPham);
            chiTiet.setGia(sanPham.getGiaSauGiam());
            chiTiet.setSoLuong(item.getSoLuong());
            chiTiet.setKhuyenMai(sanPham.getChietKhau());
            
            chiTietList.add(chiTiet);
            
            // Cập nhật tồn kho
            sanPham.setTonKho(sanPham.getTonKho() - item.getSoLuong());
            sanPhamRepository.save(sanPham);
            
            // Tính tổng tiền
            tongTien += sanPham.getGiaSauGiam() * item.getSoLuong();
        }
        
        donHang.setTongTien(tongTien);
        donHang.setChiTietDonHangs(chiTietList);
        
        // Lưu đơn hàng (cascade sẽ tự động lưu chi tiết)
        // DonHang savedOrder = donHangRepository.save(donHang);
        
        // Xóa giỏ hàng
        gioHangService.clearCart(soDienThoai);
        
        log.info("Order created successfully with total: {}", tongTien);
        
        return donHang;
    }
    
    /**
     * LẤY DANH SÁCH ĐƠN HÀNG CỦA USER
     */
    public List<DonHang> getOrdersByUser(String soDienThoai) {
        NguoiDung nguoiDung = nguoiDungRepository.findBySoDienThoai(soDienThoai)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));
        
        return nguoiDung.getDonHangs();
    }
    
    /**
     * CẬP NHẬT TRẠNG THÁI ĐƠN HÀNG
     */
    public void updateOrderStatus(Long orderId, String status) {
        // TODO: Implement khi có DonHangRepository
        log.info("Updating order {} status to {}", orderId, status);
    }
}