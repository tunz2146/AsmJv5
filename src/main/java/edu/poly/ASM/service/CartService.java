package edu.poly.ASM.service;

import edu.poly.ASM.dto.CartItemDTO;
import edu.poly.ASM.entity.Cart;
import edu.poly.ASM.entity.Product;
import edu.poly.ASM.entity.User;
import edu.poly.ASM.repository.CartRepository;
import edu.poly.ASM.repository.ProductRepository;
import edu.poly.ASM.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CartService {
    
    @Autowired
    private CartRepository cartRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    /**
     * Thêm sản phẩm vào giỏ hàng
     */
    public CartItemDTO addToCart(Long userId, Long productId, Integer quantity) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy user"));
        
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));
        
        // Kiểm tra tồn kho
        if (product.getTonKho() < quantity) {
            throw new RuntimeException("Sản phẩm không đủ số lượng");
        }
        
        // Kiểm tra sản phẩm đã có trong giỏ hàng chưa
        Cart cart = cartRepository.findByUserIdAndProductId(userId, productId)
            .orElse(new Cart());
        
        if (cart.getId() == null) {
            // Thêm mới
            cart.setUser(user);
            cart.setProduct(product);
            cart.setSoLuong(quantity);
        } else {
            // Cập nhật số lượng
            cart.setSoLuong(cart.getSoLuong() + quantity);
        }
        
        Cart saved = cartRepository.save(cart);
        return convertToDTO(saved);
    }
    
    /**
     * Cập nhật số lượng sản phẩm trong giỏ hàng
     */
    public CartItemDTO updateCartItem(Long cartId, Integer quantity) {
        Cart cart = cartRepository.findById(cartId)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy item trong giỏ hàng"));
        
        if (quantity <= 0) {
            throw new RuntimeException("Số lượng phải lớn hơn 0");
        }
        
        if (cart.getProduct().getTonKho() < quantity) {
            throw new RuntimeException("Sản phẩm không đủ số lượng");
        }
        
        cart.setSoLuong(quantity);
        Cart updated = cartRepository.save(cart);
        return convertToDTO(updated);
    }
    
    /**
     * Xóa sản phẩm khỏi giỏ hàng
     */
    public void removeFromCart(Long cartId) {
        cartRepository.deleteById(cartId);
    }
    
    /**
     * Xóa tất cả sản phẩm trong giỏ hàng của user
     */
    public void clearCart(Long userId) {
        cartRepository.clearCartByUserId(userId);
    }
    
    /**
     * Lấy giỏ hàng của user
     */
    public List<CartItemDTO> getCartByUserId(Long userId) {
        List<Cart> cartItems = cartRepository.findByUserId(userId);
        return cartItems.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    /**
     * Đếm số lượng items trong giỏ hàng
     */
    public long countCartItems(Long userId) {
        return cartRepository.countByUserId(userId);
    }
    
    /**
     * Tính tổng tiền giỏ hàng
     */
    public BigDecimal calculateCartTotal(Long userId) {
        Long total = cartRepository.calculateTotalByUserId(userId);
        return total != null ? BigDecimal.valueOf(total) : BigDecimal.ZERO;
    }
    
    /**
     * Convert Cart entity sang CartItemDTO
     */
    private CartItemDTO convertToDTO(Cart cart) {
        CartItemDTO dto = new CartItemDTO();
        dto.setId(cart.getId());
        dto.setProductId(cart.getProduct().getId());
        dto.setProductName(cart.getProduct().getTenSanPham());
        dto.setProductImage(cart.getProduct().getHinhAnh());
        dto.setProductPrice(cart.getProduct().getGiaSanPham());
        dto.setProductDiscountPrice(cart.getProduct().getGiaGiam());
        dto.setProductStock(cart.getProduct().getTonKho());
        dto.setSoLuong(cart.getSoLuong());
        
        // Tính giá cuối cùng
        BigDecimal giaCuoiCung = cart.getProduct().getGiaGiam() != null && 
                                  cart.getProduct().getGiaGiam().compareTo(BigDecimal.ZERO) > 0 ?
                                  cart.getProduct().getGiaGiam() : 
                                  cart.getProduct().getGiaSanPham();
        dto.setGiaCuoiCung(giaCuoiCung);
        
        // Tính thành tiền
        dto.setThanhTien(giaCuoiCung.multiply(BigDecimal.valueOf(cart.getSoLuong())));
        dto.setConHang(cart.getProduct().getTonKho() > 0);
        
        return dto;
    }
}