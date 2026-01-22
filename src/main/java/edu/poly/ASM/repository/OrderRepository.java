package edu.poly.ASM.repository;

import edu.poly.ASM.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    
    // Tìm đơn hàng theo mã đơn
    Optional<Order> findByMaDon(String maDon);
    
    // Lấy đơn hàng của user (có phân trang)
    Page<Order> findByUserId(Long userId, Pageable pageable);
    
    // Lấy đơn hàng theo trạng thái
    Page<Order> findByTinhTrang(Order.TinhTrang tinhTrang, Pageable pageable);
    
    // Lấy đơn hàng của user theo trạng thái
    Page<Order> findByUserIdAndTinhTrang(Long userId, Order.TinhTrang tinhTrang, Pageable pageable);
    
    // Đếm số đơn hàng theo trạng thái
    long countByTinhTrang(Order.TinhTrang tinhTrang);
    
    // Lấy đơn hàng trong khoảng thời gian
    @Query("SELECT o FROM Order o WHERE o.ngayDat BETWEEN :startDate AND :endDate")
    List<Order> findOrdersBetweenDates(
        @Param("startDate") LocalDateTime startDate, 
        @Param("endDate") LocalDateTime endDate
    );
    
    // Tính tổng doanh thu
    @Query("SELECT SUM(o.tongTien) FROM Order o WHERE o.tinhTrang = :tinhTrang")
    Long calculateTotalRevenue(@Param("tinhTrang") Order.TinhTrang tinhTrang);
    
    // Tính doanh thu theo khoảng thời gian
    @Query("SELECT SUM(o.tongTien) FROM Order o " +
           "WHERE o.ngayDat BETWEEN :startDate AND :endDate " +
           "AND o.tinhTrang = :tinhTrang")
    Long calculateRevenueByDateRange(
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate,
        @Param("tinhTrang") Order.TinhTrang tinhTrang
    );
    
    // Thống kê đơn hàng theo ngày
    @Query("SELECT DATE(o.ngayDat), COUNT(o), SUM(o.tongTien) FROM Order o " +
           "WHERE o.ngayDat BETWEEN :startDate AND :endDate " +
           "GROUP BY DATE(o.ngayDat) ORDER BY DATE(o.ngayDat)")
    List<Object[]> getOrderStatisticsByDate(
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate
    );
    
    // Tìm kiếm đơn hàng (theo mã đơn, tên người nhận, SĐT)
    @Query("SELECT o FROM Order o WHERE " +
           "o.maDon LIKE %:keyword% OR " +
           "o.tenNguoiNhan LIKE %:keyword% OR " +
           "o.soDienThoaiNhan LIKE %:keyword%")
    Page<Order> searchOrders(@Param("keyword") String keyword, Pageable pageable);
}