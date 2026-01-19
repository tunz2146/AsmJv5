package edu.poly.ASM.controller;

import java.util.List;
import java.util.ArrayList;
import edu.poly.ASM.entity.Product;
import edu.poly.ASM.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    
    @Autowired
    private ProductService productService;
    
    @GetMapping("/")
    public String home(Model model) {
        try {
            System.out.println("🏠 Loading home page...");
            
            // Lấy sản phẩm giảm giá
            List<Product> hotProducts = productService.getHotProducts(8);
            if (hotProducts == null) {
                hotProducts = new ArrayList<>();
            }
            
            // Lấy sản phẩm bán chạy
            List<Product> bestSellers = productService.getBestSellers(8);
            if (bestSellers == null) {
                bestSellers = new ArrayList<>();
            }
            
            // Nếu không có sản phẩm giảm giá, lấy 8 sản phẩm bất kỳ
            if (hotProducts.isEmpty()) {
                List<Product> allProducts = productService.getAllProducts();
                hotProducts = allProducts.stream().limit(8).toList();
            }
            
            // Nếu không có sản phẩm bán chạy, lấy 8 sản phẩm bất kỳ
            if (bestSellers.isEmpty()) {
                List<Product> allProducts = productService.getAllProducts();
                bestSellers = allProducts.stream().limit(8).toList();
            }
            
            model.addAttribute("hotProducts", hotProducts);
            model.addAttribute("bestSellers", bestSellers);
            
            System.out.println("✅ Loaded " + hotProducts.size() + " hot products");
            System.out.println("✅ Loaded " + bestSellers.size() + " best sellers");
            
        } catch (Exception e) {
            System.out.println("❌ Error loading home page: " + e.getMessage());
            e.printStackTrace();
            
            // Trả về danh sách rỗng nếu có lỗi
            model.addAttribute("hotProducts", new ArrayList<>());
            model.addAttribute("bestSellers", new ArrayList<>());
        }
        
        return "home";
    }
    
    @GetMapping("/home")
    public String homeAlternate(Model model) {
        return home(model);
    }
    
    @GetMapping("/index")
    public String index(Model model) {
        return home(model);
    }
}