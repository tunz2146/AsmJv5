package edu.poly.ASM.service;

import edu.poly.ASM.entity.Product;
import edu.poly.ASM.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;
    
    /**
     * Lấy tất cả sản phẩm đang hoạt động (có phân trang)
     */
    public Page<Product> getAllProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return productRepository.findByIsActive(true, pageable);
    }
    
    /**
     * Tìm sản phẩm theo tên
     */
    public Page<Product> searchProducts(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return productRepository.findByNameContainingIgnoreCaseAndIsActive(name, true, pageable);
    }
    
    /**
     * Lấy sản phẩm theo danh mục
     */
    public Page<Product> getProductsByCategory(Long categoryId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return productRepository.findByCategoryIdAndIsActive(categoryId, true, pageable);
    }
    
    /**
     * Lấy sản phẩm theo ID
     */
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }
    
    /**
     * Lấy danh sách sản phẩm hot (đang giảm giá)
     */
    public List<Product> getHotProducts(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return productRepository.findHotProducts(pageable);
    }
    
    /**
     * Lấy danh sách sản phẩm bán chạy
     */
    public List<Product> getBestSellers(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return productRepository.findBestSellers(pageable);
    }
    
    /**
     * Lấy sản phẩm theo khoảng giá
     */
    public Page<Product> getProductsByPrice(java.math.BigDecimal minPrice, java.math.BigDecimal maxPrice, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("price").ascending());
        return productRepository.findByPriceRange(minPrice, maxPrice, pageable);
    }
    
    /**
     * Tạo sản phẩm mới
     */
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }
    
    /**
     * Cập nhật sản phẩm
     */
    public Product updateProduct(Long id, Product productDetails) {
        Optional<Product> existingProduct = productRepository.findById(id);
        if (existingProduct.isPresent()) {
            Product product = existingProduct.get();
            product.setName(productDetails.getName());
            product.setDescription(productDetails.getDescription());
            product.setPrice(productDetails.getPrice());
            product.setDiscountPrice(productDetails.getDiscountPrice());
            product.setStockQuantity(productDetails.getStockQuantity());
            product.setImageUrl(productDetails.getImageUrl());
            product.setCategory(productDetails.getCategory());
            product.setIsActive(productDetails.getIsActive());
            return productRepository.save(product);
        }
        return null;
    }
    
    /**
     * Xóa sản phẩm
     */
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
    
    /**
     * Vô hiệu hóa sản phẩm (soft delete)
     */
    public void disableProduct(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            Product p = product.get();
            p.setIsActive(false);
            productRepository.save(p);
        }
    }
}
