package edu.poly.ASM.controller;

import edu.poly.ASM.entity.SanPham;
import edu.poly.ASM.service.SanPhamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);
    
    @Autowired
    private SanPhamService sanPhamService;
    
    /**
     * 📋 DANH SÁCH SẢN PHẨM
     */
    @GetMapping
    public String list(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String search,
            Model model) {
        
        log.info("Products list - category: {}, search: {}", category, search);
        
        List<SanPham> products;
        String pageTitle = "Tất cả sản phẩm";
        
        try {
            if (search != null && !search.isEmpty()) {
                // Tìm kiếm theo từ khóa
                products = sanPhamService.searchProducts(search);
                pageTitle = "Kết quả tìm kiếm: " + search;
                log.info("Found {} products for search: {}", products.size(), search);
                
            } else if (category != null && !category.isEmpty()) {
                // Lọc theo danh mục
                products = sanPhamService.getProductsByCategorySlug(category);
                pageTitle = "Danh mục: " + category;
                log.info("Found {} products in category: {}", products.size(), category);
                
            } else {
                // Lấy tất cả
                products = sanPhamService.getAllProducts();
                log.info("Found {} total products", products.size());
            }
            
            model.addAttribute("products", products);
            model.addAttribute("pageTitle", pageTitle + " - Gaming Shop");
            model.addAttribute("category", category);
            model.addAttribute("search", search);
            
        } catch (Exception e) {
            log.error("Error loading products", e);
            model.addAttribute("error", "Có lỗi xảy ra khi tải danh sách sản phẩm");
        }
        
        return "product/list";
    }
    
    /**
     * 👁️ CHI TIẾT SẢN PHẨM
     */
    @GetMapping("/{slug}")
    public String detail(@PathVariable String slug, Model model) {
        log.info("Product detail - slug: {}", slug);
        
        try {
            SanPham product = sanPhamService.getProductBySlug(slug)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));
            
            model.addAttribute("product", product);
            model.addAttribute("pageTitle", product.getTenSanPham() + " - Gaming Shop");
            
            // Lấy sản phẩm liên quan (cùng hãng)
            if (product.getHang() != null) {
                List<SanPham> relatedProducts = sanPhamService.getProductsByBrand(product.getHang().getId())
                        .stream()
                        .filter(p -> !p.getId().equals(product.getId()))
                        .limit(4)
                        .toList();
                model.addAttribute("relatedProducts", relatedProducts);
            }
            
        } catch (Exception e) {
            log.error("Error loading product detail", e);
            model.addAttribute("error", "Không tìm thấy sản phẩm");
            return "error";
        }
        
        return "product/detail";
    }
}