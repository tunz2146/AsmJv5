package edu.poly.ASM.repository;

import edu.poly.ASM.entity.SanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SanPhamRepository extends JpaRepository<SanPham, Long> {
    
    // Tìm sản phẩm theo slug
    Optional<SanPham> findBySlug(String slug);
    
    // Tìm sản phẩm theo hãng
    List<SanPham> findByHangId(Long hangId);
    
    // Tìm sản phẩm đang giảm giá
    @Query("SELECT sp FROM SanPham sp WHERE sp.chietKhau > 0 ORDER BY sp.chietKhau DESC")
    List<SanPham> findProductsOnSale();
    
    // Tìm sản phẩm mới nhất (top 8)
    @Query("SELECT sp FROM SanPham sp ORDER BY sp.id DESC")
    List<SanPham> findLatestProducts();
    
    // Tìm sản phẩm bán chạy (tạm thời random)
    @Query("SELECT sp FROM SanPham sp WHERE sp.tonKho > 0 ORDER BY NEWID()")
    List<SanPham> findBestSellingProducts();
    
    // Tìm sản phẩm theo tên
    @Query("SELECT sp FROM SanPham sp WHERE sp.tenSanPham LIKE %:keyword%")
    List<SanPham> searchByName(@Param("keyword") String keyword);
    
    // Tìm sản phẩm theo category slug
    @Query("SELECT sp FROM SanPham sp JOIN sp.loaiSanPhams lsp WHERE lsp.slug = :slug")
    List<SanPham> findByCategorySlug(@Param("slug") String slug);
    
    // Tìm sản phẩm trong khoảng giá
    @Query("SELECT sp FROM SanPham sp WHERE sp.giaSanPham BETWEEN :minPrice AND :maxPrice")
    List<SanPham> findByPriceRange(@Param("minPrice") Long minPrice, @Param("maxPrice") Long maxPrice);
    
    // Đếm sản phẩm theo hãng
    Long countByHangId(Long hangId);
}