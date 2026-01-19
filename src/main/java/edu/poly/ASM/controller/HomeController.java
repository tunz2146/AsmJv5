package edu.poly.ASM.controller;

import java.util.List;
import edu.poly.ASM.entity.Product;
import edu.poly.ASM.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    
    @Autowired
    private ProductService productService;
    
    /**
     * Trang chủ - route "/"
     */
@GetMapping("/")
public String home(Model model) {
    try {
        // Lấy 8 sản phẩm giảm giá
        List<Product> hotProducts = productService.getHotProducts(8);
        if (hotProducts == null || hotProducts.isEmpty()) {
            hotProducts = productService.getAllProducts().stream().limit(8).toList();
        }
        model.addAttribute("hotProducts", hotProducts);
        
        // Lấy 8 sản phẩm bán chạy
        List<Product> bestSellers = productService.getBestSellers(8);
        if (bestSellers == null || bestSellers.isEmpty()) {
            bestSellers = productService.getAllProducts().stream().limit(8).toList();
        }
        model.addAttribute("bestSellers", bestSellers);
        
        System.out.println("✅ Home page loaded with " + hotProducts.size() + " hot products");
    } catch (Exception e) {
        System.out.println("❌ Lỗi load sản phẩm: " + e.getMessage());
        e.printStackTrace();
    }
    
    return "home";
}

    /**
     * Trang chủ - route "/home"
     */
    @GetMapping("/home")
    public String homeAlternate(Model model) {
        return home(model);
    }
}
