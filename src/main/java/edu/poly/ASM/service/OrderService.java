package edu.poly.ASM.service;

import edu.poly.ASM.dto.CartItemDTO;
import edu.poly.ASM.dto.OrderDTO;
import edu.poly.ASM.dto.OrderDetailDTO;
import edu.poly.ASM.entity.Order;
import edu.poly.ASM.entity.OrderDetail;
import edu.poly.ASM.entity.Product;
import edu.poly.ASM.entity.User;
import edu.poly.ASM.repository.OrderRepository;
import edu.poly.ASM.repository.ProductRepository;
import edu.poly.ASM.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService {
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private CartService cartService;
    
    @Autowired
    private ModelMapper modelMapper;
    
    /**
     * Tạo đơn hàng từ giỏ hàng
     */
    // public OrderDTO createOrderFromCart(Long userId, OrderDTO orderDTO) {
    //     User user = userRepository.findById(userId)
    //         .orElseThrow(() -> new RuntimeException("Không tìm thấy user"));
        
    //     // Lấy giỏ hàng
    //     List<CartItemDTO> cartItems = cartService.getCartByUserId(userId);
    //     if (cartItems.isEmpty()) {
    //         throw new RuntimeException("Giỏ hàng trống");
    //     }
        
    //     // Tạo order
    //     Order order = new Order();
    //     order.setUser(user);
    //     order.setTenNguoiNhan(orderDTO.getTenNguoiNhan());
    //     order.setSoDienThoaiNhan(orderDTO.getSoDienThoaiNhan());
    //     order.setDiaChiGiaoHang(orderDTO.getDiaChiGiaoHang());
    //     order.setGhiChu(orderDTO.getGhiChu());
    //     order.setPhuongThucThanhToan(orderDTO.getPhuongThucThanhToan());
    //     order.setPhiVanChuyen(orderDTO.getPhiVanChuyen() != null ? orderDTO.getPhiVanChuyen() : BigDecimal.ZERO);
    //     order.setMaKhuyenMai(orderDTO.getMaKhuyenMai());
    //     order.setKhuyenMaiSoTienGiam(orderDTO.getKhuyenMaiSoTienGiam() != null ? orderDTO.getKhuyenMaiSoTienGiam() : BigDecimal.ZERO);
        
    //     // Tạo order details và tính tổng tiền
    //     BigDecimal tongTien = BigDecimal.ZERO;
    //     List<OrderDetail> orderDetails = new ArrayList<>();
        
    //     for (CartItemDTO cartItem : cartItems) {
    //         Product product = productRepository.findById(cartItem.getProductId())
    //             .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));
            
    //         // Kiểm tra tồn kho
    //         if (product.getTonKho() < cartItem.getSoLuong()) {
    //             throw new RuntimeException("Sản phẩm " + product.getTenSanPham() + " không đủ số lượng");
    //         }
            
    //         OrderDetail detail = new OrderDetail();
    //         detail.setOrder(order);
    //         detail.setProduct(product);
    //         detail.setGiaLucChot(cartItem.getGiaCuoiCung());
    //         detail.setSoLuong(cartItem.getSoLuong());
    //         detail.setThanhTien(cartItem.getThanhTien());
    //         detail.setKhuyenMaiLucChot(product.getChietKhau());
            
    //         orderDetails.add(detail);
    //         tongTien = tongTien.add(cartItem.getThanhTien());
            
    //         // Giảm tồn kho
    //         product.setTonKho(product.getTonKho() - cartItem.getSoLuong());
    //         productRepository.save(product);
    //     }
        
    //     order.setTongTien(tongTien);
    //     order.setOrderDetails(orderDetails);
        
    //     Order savedOrder = orderRepository.save(order);
        
    //     // Xóa giỏ hàng sau khi đặt hàng thành công
    //     cartService.clearCart(userId);
        
    //     return convertToDTO(savedOrder);
    // }
    
    // /**
    //  * Lấy đơn hàng theo ID
    //  */
    // public Optional<OrderDTO> getOrderById(Long id) {
    //     return orderRepository.findById(id)
    //         .map(this::convertToDTO);
    // }
    
    // /**
    //  * Lấy đơn hàng theo mã đơn
    //  */
    // public Optional<OrderDTO> getOrderByMaDon(String maDon) {
    //     return orderRepository.findByMaDon(maDon)
    //         .map(this::convertToDTO);
    // }
    
    // /**
    //  * Lấy đơn hàng của user
    //  */
    // public Page<OrderDTO> getOrdersByUserId(Long userId, Pageable pageable) {
    //     return orderRepository.findByUserId(userId, pageable)
    //         .map(this::convertToDTO);
    // }
    
    // /**
    //  * Lấy tất cả đơn hàng (admin)
    //  */
    // public Page<OrderDTO> getAllOrders(Pageable pageable) {
    //     return orderRepository.findAll(pageable)
    //         .map(this::convertToDTO);
    // }
    
    // /**
    //  * Cập nhật trạng thái đơn hàng
    //  */
    // public OrderDTO updateOrderStatus(Long id, Order.TinhTrang tinhTrang) {
    //     Order order = orderRepository.findById(id)
    //         .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng"));
        
    //     order.setTinhTrang(tinhTrang);
        
    //     // Nếu hủy đơn, hoàn lại tồn kho
    //     if (tinhTrang == Order.TinhTrang.DA_HUY) {
    //         for (OrderDetail detail : order.getOrderDetails()) {
    //             Product product = detail.getProduct();
    //             product.setTonKho(product.getTonKho() + detail.getSoLuong());
    //             productRepository.save(product);
    //         }
    //     }
        
    //     Order updated = orderRepository.save(order);
    //     return convertToDTO(updated);
    // }
    
    // /**
    //  * Tìm kiếm đơn hàng
    //  */
    // public Page<OrderDTO> searchOrders(String keyword, Pageable pageable) {
    //     return orderRepository.searchOrders(keyword, pageable)
    //         .map(this::convertToDTO);
    // }
    
    // /**
    //  * Convert Order entity sang OrderDTO
    //  */
    // private OrderDTO convertToDTO(Order order) {
    //     OrderDTO dto = new OrderDTO();
    //     dto.setId(order.getId());
    //     dto.setMaDon(order.getMaDon());
    //     dto.setNgayDat(order.getNgayDat());
    //     dto.setTinhTrang(order.getTinhTrang().name());
    //     dto.setTongTien(order.getTongTien());
    //     dto.setGhiChu(order.getGhiChu());
    //     dto.setPhuongThucThanhToan(order.getPhuongThucThanhToan());
    //     dto.setTrangThaiThanhToan(order.getTrangThaiThanhToan());
    //     dto.setTenNguoiNhan(order.getTenNguoiNhan());
    //     dto.setSoDienThoaiNhan(order.getSoDienThoaiNhan());
    //     dto.setDiaChiGiaoHang(order.getDiaChiGiaoHang());
    //     dto.setDonViVanChuyen(order.getDonViVanChuyen());
    //     dto.setMaVanDon(order.getMaVanDon());
    //     dto.setPhiVanChuyen(order.getPhiVanChuyen());
    //     dto.setKhuyenMaiSoTienGiam(order.getKhuyenMaiSoTienGiam());
    //     dto.setMaKhuyenMai(order.getMaKhuyenMai());
    //     dto.setUserId(order.getUser().getId());
    //     dto.setUserName(order.getUser().getTen());
        
    //     // Tính tổng thanh toán
    //     dto.setTongThanhToan(order.getTongThanhToan());
        
    //     // Convert order details
    //     List<OrderDetailDTO> detailDTOs = order.getOrderDetails().stream()
    //         .map(this::convertDetailToDTO)
    //         .collect(Collectors.toList());
    //     dto.setOrderDetails(detailDTOs);
        
    //     return dto;
    // }
    
    // private OrderDetailDTO convertDetailToDTO(OrderDetail detail) {
    //     OrderDetailDTO dto = new OrderDetailDTO();
    //     dto.setId(detail.getId());
    //     dto.setProductId(detail.getProduct().getId());
    //     dto.setProductName(detail.getProduct().getTenSanPham());
    //     dto.setProductImage(detail.getProduct().getHinhAnh());
    //     dto.setGiaLucChot(detail.getGiaLucChot());
    //     dto.setSoLuong(detail.getSoLuong());
    //     dto.setThanhTien(detail.getThanhTien());
    //     dto.setKhuyenMaiLucChot(detail.getKhuyenMaiLucChot());
    //     return dto;
    // }
}