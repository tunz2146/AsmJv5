package edu.poly.ASM.controller;

import edu.poly.ASM.entity.Product;
import edu.poly.ASM.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/san-pham")
public class ProductController {
    
    @Autowired
    private ProductService productService;
    
    /**
     * Hiển thị danh sách sản phẩm
     */
    @GetMapping("")
public String listProducts(
    @RequestParam(defaultValue = "0") int page,
    Model model
) {
    Page<Product> products = productService.getAllProducts(page, 12);
    
    model.addAttribute("products", products.getContent());
    model.addAttribute("totalElements", products.getTotalElements());
    model.addAttribute("currentPage", page);
    model.addAttribute("totalPages", products.getTotalPages());
    
    return "product/list";
}
    
    /**
     * Tìm kiếm sản phẩm
     */
    @GetMapping("/tim-kiem")
    public String searchProducts(@RequestParam(defaultValue = "") String keyword,
                                @RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "12") int size,
                                Model model) {
        Page<Product> products;
        
        if (keyword == null || keyword.trim().isEmpty()) {
            products = productService.getAllProducts(page, size);
        } else {
            products = productService.searchProducts(keyword, page, size);
        }
        
        model.addAttribute("products", products);
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", products.getTotalPages());
        model.addAttribute("totalElements", products.getTotalElements());
        model.addAttribute("pageTitle", "Kết quả tìm kiếm");
        return "product/list";
    }
    
    /**
     * Hiển thị sản phẩm theo danh mục
     */
    @GetMapping("/danh-muc/{categoryId}")
    public String productsByCategory(@PathVariable Long categoryId,
                                     @RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "12") int size,
                                     Model model) {
        Page<Product> products = productService.getProductsByCategory(categoryId, page, size);
        model.addAttribute("products", products);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", products.getTotalPages());
        model.addAttribute("totalElements", products.getTotalElements());
        model.addAttribute("pageTitle", "Sản phẩm theo danh mục");
        return "product/list";
    }
    
    /**
     * Hiển thị chi tiết sản phẩm
     */
    @GetMapping("/{id}")
public String productDetail(@PathVariable Long id, Model model) {
    Product product = productService.getProductById(id)
        .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));
    
    // Nếu sản phẩm bị ẩn → redirect
    if (!product.getIsActive()) {
        return "redirect:/san-pham";
    }
    
    model.addAttribute("product", product);
    return "product/detail";
}
}
