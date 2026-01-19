package edu.poly.ASM.repository;

import edu.poly.ASM.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    // Tìm sản phẩm theo tên (không phân biệt hoa thường) và trạng thái active
    Page<Product> findByNameContainingIgnoreCaseAndIsActive(String name, Boolean isActive, Pageable pageable);
    
    // Tìm sản phẩm theo danh mục và trạng thái active
    Page<Product> findByCategoryIdAndIsActive(Long categoryId, Boolean isActive, Pageable pageable);
    
    // Lấy tất cả sản phẩm đang active
    Page<Product> findByIsActive(Boolean isActive, Pageable pageable);
    
    // Lấy tất cả sản phẩm đang active (không phân trang)
    List<Product> findByIsActive(Boolean isActive);
    
    // Lấy sản phẩm đang giảm giá (hot products) - trả về List<Product>
    @Query("SELECT p FROM Product p WHERE p.isActive = true AND p.discountPrice IS NOT NULL AND p.discountPrice > 0 ORDER BY p.discountPrice DESC")
    List<Product> findHotProducts(Pageable pageable);
    
    // Lấy sản phẩm bán chạy (mới nhất) - trả về List<Product>
    @Query("SELECT p FROM Product p WHERE p.isActive = true ORDER BY p.id DESC")
    List<Product> findBestSellers(Pageable pageable);
    
    // Tìm sản phẩm theo khoảng giá
    @Query("SELECT p FROM Product p WHERE p.isActive = true AND p.price BETWEEN :minPrice AND :maxPrice")
    Page<Product> findByPriceRange(@Param("minPrice") BigDecimal minPrice, @Param("maxPrice") BigDecimal maxPrice, Pageable pageable);
}
