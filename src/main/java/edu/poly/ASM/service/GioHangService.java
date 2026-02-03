package edu.poly.ASM.service;

import edu.poly.ASM.entity.GioHang;
import edu.poly.ASM.entity.NguoiDung;
import edu.poly.ASM.entity.SanPham;
import edu.poly.ASM.repository.GioHangRepository;
import edu.poly.ASM.repository.NguoiDungRepository;
import edu.poly.ASM.repository.SanPhamRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GioHangService {
    
    private static final Logger log = LoggerFactory.getLogger(GioHangService.class);
    
    @Autowired
    private GioHangRepository gioHangRepository;
    
    @Autowired
    private SanPhamRepository sanPhamRepository;
    
    @Autowired
    private NguoiDungRepository nguoiDungRepository;
    
    /**
     * THÊM SẢN PHẨM VÀO GIỎ HÀNG
     */
    public GioHang addToCart(String soDienThoai, Long sanPhamId, Integer soLuong) {
        log.info("Adding product {} to cart for user {}", sanPhamId, soDienThoai);
        
        // Tìm người dùng
        NguoiDung nguoiDung = nguoiDungRepository.findBySoDienThoai(soDienThoai)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));
        
        // Tìm sản phẩm
        SanPham sanPham = sanPhamRepository.findById(sanPhamId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));
        
        // Kiểm tra tồn kho
        if (sanPham.getTonKho() == null || sanPham.getTonKho() < soLuong) {
            throw new RuntimeException("Sản phẩm không đủ số lượng trong kho");
        }
        
        // Kiểm tra xem sản phẩm đã có trong giỏ chưa
        Optional<GioHang> existingCart = gioHangRepository
                .findByNguoiDungIdAndSanPhamId(nguoiDung.getId(), sanPhamId);
        
        GioHang gioHang;
        if (existingCart.isPresent()) {
            // Nếu đã có thì tăng số lượng
            gioHang = existingCart.get();
            int newQuantity = gioHang.getSoLuong() + soLuong;
            
            // Kiểm tra lại tồn kho với số lượng mới
            if (sanPham.getTonKho() < newQuantity) {
                throw new RuntimeException("Vượt quá số lượng tồn kho");
            }
            
            gioHang.setSoLuong(newQuantity);
            log.info("Updated cart item quantity to {}", newQuantity);
        } else {
            // Nếu chưa có thì tạo mới
            gioHang = new GioHang();
            gioHang.setNguoiDung(nguoiDung);
            gioHang.setSanPham(sanPham);
            gioHang.setSoLuong(soLuong);
            log.info("Created new cart item");
        }
        
        return gioHangRepository.save(gioHang);
    }
    
    /**
     * LẤY GIỎ HÀNG CỦA USER
     */
    public List<GioHang> getCartByUser(String soDienThoai) {
        NguoiDung nguoiDung = nguoiDungRepository.findBySoDienThoai(soDienThoai)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));
        
        return gioHangRepository.findByNguoiDungId(nguoiDung.getId());
    }
    
    /**
     * CẬP NHẬT SỐ LƯỢNG
     */
    public GioHang updateQuantity(Long cartId, Integer soLuong) {
        GioHang gioHang = gioHangRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giỏ hàng"));
        
        // Kiểm tra tồn kho
        if (gioHang.getSanPham().getTonKho() < soLuong) {
            throw new RuntimeException("Vượt quá số lượng tồn kho");
        }
        
        gioHang.setSoLuong(soLuong);
        return gioHangRepository.save(gioHang);
    }
    
    /**
     * XÓA SẢN PHẨM KHỎI GIỎ
     */
    public void removeFromCart(Long cartId) {
        gioHangRepository.deleteById(cartId);
        log.info("Removed cart item {}", cartId);
    }
    
    /**
     * XÓA TOÀN BỘ GIỎ HÀNG
     */
    public void clearCart(String soDienThoai) {
        NguoiDung nguoiDung = nguoiDungRepository.findBySoDienThoai(soDienThoai)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));
        
        gioHangRepository.deleteByNguoiDungId(nguoiDung.getId());
        log.info("Cleared cart for user {}", soDienThoai);
    }
    
    /**
     * TÍNH TỔNG TIỀN GIỎ HÀNG
     */
    public Long calculateTotal(List<GioHang> cartItems) {
        return cartItems.stream()
                .mapToLong(item -> {
                    SanPham sp = item.getSanPham();
                    long price = sp.getGiaSauGiam();
                    return price * item.getSoLuong();
                })
                .sum();
    }
    
    /**
     * ĐẾM SỐ LƯỢNG ITEM TRONG GIỎ
     */
    public Long countCartItems(String soDienThoai) {
        try {
            NguoiDung nguoiDung = nguoiDungRepository.findBySoDienThoai(soDienThoai)
                    .orElse(null);
            
            if (nguoiDung == null) {
                return 0L;
            }
            
            return gioHangRepository.countByNguoiDungId(nguoiDung.getId());
        } catch (Exception e) {
            log.error("Error counting cart items", e);
            return 0L;
        }
    }
}