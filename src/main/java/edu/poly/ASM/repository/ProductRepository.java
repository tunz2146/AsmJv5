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
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    // Tìm sản phẩm theo slug
    Optional<Product> findBySlug(String slug);
    
    // Tìm sản phẩm theo tên (có phân trang)
    Page<Product> findByTenSanPhamContainingIgnoreCaseAndTrangThai(
        String tenSanPham, Boolean trangThai, Pageable pageable
    );
    
    // Tìm sản phẩm theo danh mục (Many-to-Many)
    @Query("SELECT p FROM Product p JOIN p.categories c " +
           "WHERE c.id = :categoryId AND p.trangThai = true")
    Page<Product> findByCategoryId(@Param("categoryId") Long categoryId, Pageable pageable);
    
    // Tìm sản phẩm theo hãng
    Page<Product> findByBrandIdAndTrangThai(Long brandId, Boolean trangThai, Pageable pageable);
    
    // Lấy sản phẩm đang hiển thị
    Page<Product> findByTrangThai(Boolean trangThai, Pageable pageable);
    
    // Lấy sản phẩm đang giảm giá (hot products)
    @Query("SELECT p FROM Product p WHERE p.trangThai = true " +
           "AND p.giaGiam IS NOT NULL AND p.giaGiam > 0 " +
           "ORDER BY p.chietKhau DESC")
    List<Product> findHotProducts(Pageable pageable);
    
    // Lấy sản phẩm bán chạy nhất (dựa trên lượt xem)
    @Query("SELECT p FROM Product p WHERE p.trangThai = true " +
           "ORDER BY p.luotXem DESC")
    List<Product> findBestSellers(Pageable pageable);
    
    // Lấy sản phẩm mới nhất
    @Query("SELECT p FROM Product p WHERE p.trangThai = true " +
           "ORDER BY p.createdAt DESC")
    List<Product> findLatestProducts(Pageable pageable);
    
    // Tìm sản phẩm theo khoảng giá
    @Query("SELECT p FROM Product p WHERE p.trangThai = true " +
           "AND ((p.giaGiam IS NOT NULL AND p.giaGiam BETWEEN :minPrice AND :maxPrice) " +
           "OR (p.giaGiam IS NULL AND p.giaSanPham BETWEEN :minPrice AND :maxPrice))")
    Page<Product> findByPriceRange(
        @Param("minPrice") BigDecimal minPrice, 
        @Param("maxPrice") BigDecimal maxPrice, 
        Pageable pageable
    );
    
    // Tìm kiếm sản phẩm nâng cao
    @Query("SELECT DISTINCT p FROM Product p LEFT JOIN p.categories c LEFT JOIN p.brand b " +
           "WHERE p.trangThai = true " +
           "AND (LOWER(p.tenSanPham) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(p.moTaNgan) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(c.ten) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(b.tenHang) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Product> searchProducts(@Param("keyword") String keyword, Pageable pageable);
    
    // Lấy sản phẩm liên quan (cùng danh mục)
    @Query("SELECT DISTINCT p FROM Product p JOIN p.categories c " +
           "WHERE c.id IN (SELECT c2.id FROM Product p2 JOIN p2.categories c2 WHERE p2.id = :productId) " +
           "AND p.id != :productId AND p.trangThai = true")
    List<Product> findRelatedProducts(@Param("productId") Long productId, Pageable pageable);
    
    // Đếm sản phẩm theo danh mục
    @Query("SELECT COUNT(p) FROM Product p JOIN p.categories c " +
           "WHERE c.id = :categoryId AND p.trangThai = true")
    long countByCategoryId(@Param("categoryId") Long categoryId);
    
    // Đếm sản phẩm theo hãng
    long countByBrandIdAndTrangThai(Long brandId, Boolean trangThai);
    
    // Lấy sản phẩm đang hết hàng
    @Query("SELECT p FROM Product p WHERE p.trangThai = true AND p.tonKho <= :threshold")
    List<Product> findLowStockProducts(@Param("threshold") Integer threshold);
    
    // Custom methods for English naming compatibility
    default Page<Product> findByIsActive(boolean isActive, Pageable pageable) {
        return findByTrangThai(isActive, pageable);
    }
    
    default List<Product> findByIsActive(boolean isActive) {
        return findByTrangThai(isActive, org.springframework.data.domain.Pageable.unpaged())
            .getContent();
    }
    
    default Page<Product> findByNameContainingIgnoreCaseAndIsActive(
        String name, boolean isActive, Pageable pageable) {
        return findByTenSanPhamContainingIgnoreCaseAndTrangThai(name, isActive, pageable);
    }
    
    default Page<Product> findByCategoryIdAndIsActive(
        Long categoryId, boolean isActive, Pageable pageable) {
        return findByCategoryId(categoryId, pageable);
    }
}