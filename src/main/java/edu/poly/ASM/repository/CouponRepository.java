package edu.poly.ASM.repository;

import edu.poly.ASM.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {
    
    // Tìm coupon theo mã
    Optional<Coupon> findByMaKhuyenMai(String maKhuyenMai);
    
    // Kiểm tra mã coupon đã tồn tại chưa
    boolean existsByMaKhuyenMai(String maKhuyenMai);
    
    // Lấy các coupon đang hoạt động
    @Query("SELECT c FROM Coupon c WHERE c.trangThai = true " +
           "AND c.ngayBatDau <= :now AND c.ngayKetThuc >= :now " +
           "AND c.daSuDung < c.soLuongMa")
    List<Coupon> findActiveCoupons(@Param("now") LocalDateTime now);
    
    // Lấy coupon có thể sử dụng cho đơn hàng
    @Query("SELECT c FROM Coupon c WHERE c.trangThai = true " +
           "AND c.ngayBatDau <= :now AND c.ngayKetThuc >= :now " +
           "AND c.daSuDung < c.soLuongMa " +
           "AND c.giaTriDonHangToiThieu <= :orderValue")
    List<Coupon> findAvailableCouponsForOrder(
        @Param("now") LocalDateTime now, 
        @Param("orderValue") java.math.BigDecimal orderValue
    );
    
    // Lấy coupon sắp hết hạn
    @Query("SELECT c FROM Coupon c WHERE c.trangThai = true " +
           "AND c.ngayKetThuc BETWEEN :now AND :endDate")
    List<Coupon> findExpiringSoon(
        @Param("now") LocalDateTime now, 
        @Param("endDate") LocalDateTime endDate
    );
}