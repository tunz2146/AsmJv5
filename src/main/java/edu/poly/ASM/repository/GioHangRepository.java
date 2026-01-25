package edu.poly.ASM.repository;

import edu.poly.ASM.entity.GioHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GioHangRepository extends JpaRepository<GioHang, Long> {
    
    // Tìm giỏ hàng theo người dùng
    List<GioHang> findByNguoiDungId(Long nguoiDungId);
    
    // Tìm item trong giỏ hàng
    Optional<GioHang> findByNguoiDungIdAndSanPhamId(Long nguoiDungId, Long sanPhamId);
    
    // Đếm số lượng item trong giỏ
    Long countByNguoiDungId(Long nguoiDungId);
    
    // Tính tổng tiền giỏ hàng
    @Query("SELECT SUM(gh.soLuong * sp.giaSanPham) FROM GioHang gh JOIN gh.sanPham sp WHERE gh.nguoiDung.id = :nguoiDungId")
    Long calculateTotalByNguoiDungId(@Param("nguoiDungId") Long nguoiDungId);
    
    // Xóa tất cả item trong giỏ của user
    void deleteByNguoiDungId(Long nguoiDungId);
}