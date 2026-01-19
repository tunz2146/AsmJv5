package edu.poly.ASM.repository;

import edu.poly.ASM.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    
    // Tìm danh mục theo tên
    Page<Category> findByNameContainingIgnoreCaseAndIsActive(String name, Boolean isActive, Pageable pageable);
    
    // Lấy danh mục đang hoạt động
    Page<Category> findByIsActive(Boolean isActive, Pageable pageable);
    
    // Lấy tất cả danh mục đang hoạt động (không phân trang)
    java.util.List<Category> findByIsActive(Boolean isActive);
}
