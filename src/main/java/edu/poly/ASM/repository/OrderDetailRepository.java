package edu.poly.ASM.repository;

import edu.poly.ASM.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    
    // Lấy chi tiết đơn hàng theo order ID
    List<OrderDetail> findByOrderId(Long orderId);
    
    // Lấy sản phẩm bán chạy nhất
    @Query("SELECT od.product.id, od.product.tenSanPham, SUM(od.soLuong) as totalSold " +
           "FROM OrderDetail od " +
           "GROUP BY od.product.id, od.product.tenSanPham " +
           "ORDER BY totalSold DESC")
    List<Object[]> findBestSellingProducts();
    
    // Thống kê doanh thu theo sản phẩm
    @Query("SELECT od.product.id, od.product.tenSanPham, " +
           "SUM(od.soLuong) as totalQuantity, SUM(od.thanhTien) as totalRevenue " +
           "FROM OrderDetail od " +
           "GROUP BY od.product.id, od.product.tenSanPham " +
           "ORDER BY totalRevenue DESC")
    List<Object[]> getProductRevenueStatistics();
    
    // Kiểm tra user đã mua sản phẩm chưa
    @Query("SELECT CASE WHEN COUNT(od) > 0 THEN true ELSE false END " +
           "FROM OrderDetail od " +
           "WHERE od.order.user.id = :userId " +
           "AND od.product.id = :productId " +
           "AND od.order.tinhTrang = 'DA_GIAO'")
    boolean hasUserPurchasedProduct(
        @Param("userId") Long userId, 
        @Param("productId") Long productId
    );
}