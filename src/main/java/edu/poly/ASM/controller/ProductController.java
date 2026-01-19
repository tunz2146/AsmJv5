package edu.poly.ASM.controller;

import edu.poly.ASM.entity.Product;
import edu.poly.ASM.entity.Category;
import edu.poly.ASM.service.ProductService;
import edu.poly.ASM.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/san-pham")
public class ProductController {
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private CategoryService categoryService;
    
    /**
     * Hiển thị danh sách sản phẩm
     */
    @GetMapping("")
    public String listProducts(
        @RequestParam(defaultValue = "0") int page,
        Model model
    ) {
        try {
            Page<Product> productsPage = productService.getAllProducts(page, 12);
            List<Category> categories = categoryService.getAllCategories();
            
            model.addAttribute("products", productsPage.getContent());
            model.addAttribute("categories", categories);
            model.addAttribute("totalElements", productsPage.getTotalElements());
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", productsPage.getTotalPages());
            model.addAttribute("pageTitle", "Tất cả sản phẩm");
            
            System.out.println("✅ Loaded " + productsPage.getContent().size() + " products");
        } catch (Exception e) {
            System.out.println("❌ Error loading products: " + e.getMessage());
            e.printStackTrace();
        }
        
        return "product/list";
    }
    
    /**
     * Tìm kiếm sản phẩm
     */
    @GetMapping("/tim-kiem")
    public String searchProducts(
        @RequestParam(defaultValue = "") String keyword,
        @RequestParam(defaultValue = "0") int page,
        Model model
    ) {
        try {
            Page<Product> productsPage;
            List<Category> categories = categoryService.getAllCategories();
            
            if (keyword == null || keyword.trim().isEmpty()) {
                productsPage = productService.getAllProducts(page, 12);
            } else {
                productsPage = productService.searchProducts(keyword, page, 12);
            }
            
            model.addAttribute("products", productsPage.getContent());
            model.addAttribute("categories", categories);
            model.addAttribute("keyword", keyword);
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", productsPage.getTotalPages());
            model.addAttribute("totalElements", productsPage.getTotalElements());
            model.addAttribute("pageTitle", "Kết quả tìm kiếm: " + keyword);
            
            System.out.println("✅ Search found " + productsPage.getContent().size() + " products");
        } catch (Exception e) {
            System.out.println("❌ Error searching products: " + e.getMessage());
            e.printStackTrace();
        }
        
        return "product/list";
    }
    
    /**
     * Hiển thị sản phẩm theo danh mục
     */
    @GetMapping("/danh-muc/{categoryId}")
    public String productsByCategory(
        @PathVariable Long categoryId,
        @RequestParam(defaultValue = "0") int page,
        Model model
    ) {
        try {
            Page<Product> productsPage = productService.getProductsByCategory(categoryId, page, 12);
            List<Category> categories = categoryService.getAllCategories();
            Category currentCategory = categoryService.getCategoryById(categoryId).orElse(null);
            
            model.addAttribute("products", productsPage.getContent());
            model.addAttribute("categories", categories);
            model.addAttribute("category", currentCategory);
            model.addAttribute("categoryId", categoryId);
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", productsPage.getTotalPages());
            model.addAttribute("totalElements", productsPage.getTotalElements());
            model.addAttribute("pageTitle", currentCategory != null ? currentCategory.getName() : "Danh mục");
            
            System.out.println("✅ Loaded " + productsPage.getContent().size() + " products in category");
        } catch (Exception e) {
            System.out.println("❌ Error loading category products: " + e.getMessage());
            e.printStackTrace();
        }
        
        return "product/list";
    }
    
    /**
     * Hiển thị chi tiết sản phẩm
     */
    @GetMapping("/{id}")
    public String productDetail(@PathVariable Long id, Model model) {
        try {
            Product product = productService.getProductById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));
            
            // Nếu sản phẩm bị ẩn → redirect
            if (!product.getIsActive()) {
                return "redirect:/san-pham";
            }
            
            // Lấy sản phẩm liên quan (cùng danh mục)
            List<Product> relatedProducts = productService.getProductsByCategory(
                product.getCategory().getId(), 0, 4
            ).getContent();
            
            model.addAttribute("product", product);
            model.addAttribute("relatedProducts", relatedProducts);
            model.addAttribute("pageTitle", product.getName());
            
            System.out.println("✅ Loaded product detail: " + product.getName());
        } catch (Exception e) {
            System.out.println("❌ Error loading product detail: " + e.getMessage());
            e.printStackTrace();
            return "redirect:/san-pham";
        }
        
        return "product/detail";
    }
}