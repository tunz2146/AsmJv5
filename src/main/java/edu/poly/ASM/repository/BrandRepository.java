package edu.poly.ASM.repository;

import edu.poly.ASM.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    
    // Tìm hãng theo slug
    Optional<Brand> findBySlug(String slug);
    
    // Tìm hãng theo tên (không phân biệt hoa thường)
    List<Brand> findByTenHangContainingIgnoreCase(String tenHang);
    
    // Kiểm tra hãng đã tồn tại chưa
    boolean existsByTenHang(String tenHang);
}