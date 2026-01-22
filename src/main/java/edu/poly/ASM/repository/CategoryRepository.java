package edu.poly.ASM.repository;

import edu.poly.ASM.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    
    // Tìm danh mục theo slug
    Optional<Category> findBySlug(String slug);
    
    // Tìm danh mục theo tên
    List<Category> findByTenContainingIgnoreCase(String ten);
    
    // Kiểm tra danh mục đã tồn tại chưa
    boolean existsByTen(String ten);
    
    // Lấy danh mục có sản phẩm
    @Query("SELECT DISTINCT c FROM Category c JOIN c.products p WHERE p.trangThai = true")
    List<Category> findCategoriesWithProducts();
    
    // Đếm số lượng sản phẩm trong mỗi danh mục
    @Query("SELECT c.id, c.ten, COUNT(p) FROM Category c " +
           "LEFT JOIN c.products p WHERE p.trangThai = true " +
           "GROUP BY c.id, c.ten")
    List<Object[]> countProductsByCategory();
    
    // Custom methods for English naming compatibility
    default List<Category> findByIsActive(boolean isActive) {
        return findCategoriesWithProducts();
    }
}