package edu.poly.ASM.repository;

import edu.poly.ASM.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    
    // Lấy tất cả items trong giỏ hàng của user
    List<Cart> findByUserId(Long userId);
    
    // Tìm item cụ thể trong giỏ hàng
    Optional<Cart> findByUserIdAndProductId(Long userId, Long productId);
    
    // Đếm số lượng items trong giỏ hàng
    long countByUserId(Long userId);
    
    // Xóa tất cả items trong giỏ hàng của user
    @Modifying
    @Query("DELETE FROM Cart c WHERE c.user.id = :userId")
    void clearCartByUserId(@Param("userId") Long userId);
    
    // Xóa item cụ thể
    void deleteByUserIdAndProductId(Long userId, Long productId);
    
    // Tính tổng tiền giỏ hàng
    @Query("SELECT SUM(c.soLuong * " +
           "CASE WHEN p.giaGiam IS NOT NULL AND p.giaGiam > 0 " +
           "THEN p.giaGiam ELSE p.giaSanPham END) " +
           "FROM Cart c JOIN c.product p WHERE c.user.id = :userId")
    Long calculateTotalByUserId(@Param("userId") Long userId);
}