package edu.poly.ASM.controller.admin;

import edu.poly.ASM.entity.Category;
import edu.poly.ASM.entity.Product;
import edu.poly.ASM.service.CategoryService;
import edu.poly.ASM.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/products")
public class AdminProductController {
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private CategoryService categoryService;
    
    /**
     * Hiển thị danh sách sản phẩm (admin)
     */
    @GetMapping("")
    public String list(@RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "10") int size,
                       Model model) {
        Page<Product> products = productService.getAllProducts(page, size);
        model.addAttribute("products", products);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", products.getTotalPages());
        model.addAttribute("pageTitle", "Quản lý sản phẩm");
        return "admin/product/list";
    }
    
    /**
     * Tìm kiếm sản phẩm (admin)
     */
    @GetMapping("/search")
    public String search(@RequestParam(defaultValue = "") String keyword,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "10") int size,
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
        model.addAttribute("pageTitle", "Tìm kiếm sản phẩm");
        return "admin/product/list";
    }
    
    /**
     * Hiển thị form thêm sản phẩm mới
     */
    @GetMapping("/create")
    public String createForm(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categories);
        model.addAttribute("pageTitle", "Thêm sản phẩm mới");
        return "admin/product/form";
    }
    
    /**
     * Lưu sản phẩm mới
     */
    @PostMapping("/create")
    public String create(@ModelAttribute Product product) {
        if (product.getIsActive() == null) {
            product.setIsActive(true);
        }
        productService.createProduct(product);
        return "redirect:/admin/products?success=create";
    }
    
    /**
     * Hiển thị form sửa sản phẩm
     */
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Optional<Product> product = productService.getProductById(id);
        if (!product.isPresent()) {
            return "redirect:/admin/products?error=not-found";
        }
        
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("product", product.get());
        model.addAttribute("categories", categories);
        model.addAttribute("pageTitle", "Sửa sản phẩm: " + product.get().getName());
        return "admin/product/form";
    }
    
    /**
     * Lưu sản phẩm sau khi sửa
     */
    @PostMapping("/{id}/update")
    public String update(@PathVariable Long id, @ModelAttribute Product productDetails) {
        Product updated = productService.updateProduct(id, productDetails);
        if (updated != null) {
            return "redirect:/admin/products?success=update";
        }
        return "redirect:/admin/products?error=update";
    }
    
    /**
     * Xóa sản phẩm (soft delete - chỉ set isActive = false)
     */
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        productService.disableProduct(id);
        return "redirect:/admin/products?success=delete";
    }
    
    /**
     * Xem chi tiết sản phẩm (admin)
     */
    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Optional<Product> product = productService.getProductById(id);
        if (!product.isPresent()) {
            return "redirect:/admin/products?error=not-found";
        }
        
        model.addAttribute("product", product.get());
        model.addAttribute("pageTitle", "Chi tiết: " + product.get().getName());
        return "admin/product/detail";
    }
}
