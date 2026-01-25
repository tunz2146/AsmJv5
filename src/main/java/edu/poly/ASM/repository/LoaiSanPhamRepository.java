package edu.poly.ASM.repository;

import edu.poly.ASM.entity.LoaiSanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoaiSanPhamRepository extends JpaRepository<LoaiSanPham, Long> {
    
    // Tìm loại sản phẩm theo slug
    Optional<LoaiSanPham> findBySlug(String slug);
    
    // Tìm loại sản phẩm theo tên
    Optional<LoaiSanPham> findByTen(String ten);
}