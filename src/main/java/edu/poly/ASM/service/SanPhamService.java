package edu.poly.ASM.service;

import edu.poly.ASM.entity.SanPham;
import edu.poly.ASM.repository.SanPhamRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SanPhamService {
    
    private static final Logger log = LoggerFactory.getLogger(SanPhamService.class);
    
    private final SanPhamRepository sanPhamRepository;
    
    // Constructor injection
    @Autowired
    public SanPhamService(SanPhamRepository sanPhamRepository) {
        this.sanPhamRepository = sanPhamRepository;
    }
    
    // Lấy tất cả sản phẩm
    public List<SanPham> getAllProducts() {
        log.info("Getting all products");
        return sanPhamRepository.findAll();
    }
    
    // Lấy sản phẩm theo ID
    public Optional<SanPham> getProductById(Long id) {
        log.info("Getting product by id: {}", id);
        return sanPhamRepository.findById(id);
    }
    
    // Lấy sản phẩm theo slug
    public Optional<SanPham> getProductBySlug(String slug) {
        log.info("Getting product by slug: {}", slug);
        return sanPhamRepository.findBySlug(slug);
    }
    
    // Lấy sản phẩm mới nhất (limit)
    public List<SanPham> getLatestProducts(int limit) {
        log.info("Getting latest {} products", limit);
        Pageable pageable = PageRequest.of(0, limit);
        return sanPhamRepository.findLatestProducts().stream()
                .limit(limit)
                .toList();
    }
    
    // Lấy sản phẩm đang giảm giá
    public List<SanPham> getProductsOnSale(int limit) {
        log.info("Getting {} products on sale", limit);
        return sanPhamRepository.findProductsOnSale().stream()
                .limit(limit)
                .toList();
    }
    
    // Lấy sản phẩm bán chạy
    public List<SanPham> getBestSellingProducts(int limit) {
        log.info("Getting {} best selling products", limit);
        return sanPhamRepository.findBestSellingProducts().stream()
                .limit(limit)
                .toList();
    }
    
    // Tìm kiếm sản phẩm
    public List<SanPham> searchProducts(String keyword) {
        log.info("Searching products with keyword: {}", keyword);
        return sanPhamRepository.searchByName(keyword);
    }
    
    // Lấy sản phẩm theo category slug
    public List<SanPham> getProductsByCategorySlug(String slug) {
        log.info("Getting products by category slug: {}", slug);
        return sanPhamRepository.findByCategorySlug(slug);
    }
    
    // Lấy sản phẩm theo hãng
    public List<SanPham> getProductsByBrand(Long hangId) {
        log.info("Getting products by brand id: {}", hangId);
        return sanPhamRepository.findByHangId(hangId);
    }
    
    // Lấy sản phẩm theo khoảng giá
    public List<SanPham> getProductsByPriceRange(Long minPrice, Long maxPrice) {
        log.info("Getting products with price range: {} - {}", minPrice, maxPrice);
        return sanPhamRepository.findByPriceRange(minPrice, maxPrice);
    }
    
    // Lưu sản phẩm
    @Transactional
    public SanPham saveProduct(SanPham sanPham) {
        log.info("Saving product: {}", sanPham.getTenSanPham());
        return sanPhamRepository.save(sanPham);
    }
    
    // Xóa sản phẩm
    @Transactional
    public void deleteProduct(Long id) {
        log.info("Deleting product with id: {}", id);
        sanPhamRepository.deleteById(id);
    }
    
    // Kiểm tra sản phẩm tồn tại
    public boolean existsById(Long id) {
        return sanPhamRepository.existsById(id);
    }
    
    // Đếm tổng số sản phẩm
    public long countAllProducts() {
        return sanPhamRepository.count();
    }
}