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
            long startTime = System.currentTimeMillis();
            
            // Lấy sản phẩm giảm giá (Hot Products)
            List<Product> hotProducts = new ArrayList<>();
            try {
                hotProducts = productService.getHotProducts(8);
                System.out.println("✅ Hot products loaded: " + (hotProducts != null ? hotProducts.size() : 0));
            } catch (Exception e) {
                System.err.println("⚠️  Warning loading hot products: " + e.getMessage());
                hotProducts = new ArrayList<>();
            }
            
            // Lấy sản phẩm bán chạy (Best Sellers)
            List<Product> bestSellers = new ArrayList<>();
            try {
                bestSellers = productService.getBestSellers(8);
                System.out.println("✅ Best sellers loaded: " + (bestSellers != null ? bestSellers.size() : 0));
            } catch (Exception e) {
                System.err.println("⚠️  Warning loading best sellers: " + e.getMessage());
                bestSellers = new ArrayList<>();
            }
            
            // Nếu không có sản phẩm giảm giá, lấy sản phẩm bất kỳ
            if (hotProducts.isEmpty()) {
                try {
                    List<Product> allProducts = productService.getAllProducts();
                    if (allProducts != null && !allProducts.isEmpty()) {
                        hotProducts = allProducts.subList(0, Math.min(8, allProducts.size()));
                        System.out.println("ℹ️  Using regular products as hot products: " + hotProducts.size());
                    }
                } catch (Exception e) {
                    System.err.println("⚠️  Warning loading all products: " + e.getMessage());
                }
            }
            
            // Nếu không có sản phẩm bán chạy, lấy sản phẩm bất kỳ
            if (bestSellers.isEmpty()) {
                try {
                    List<Product> allProducts = productService.getAllProducts();
                    if (allProducts != null && !allProducts.isEmpty()) {
                        bestSellers = allProducts.subList(0, Math.min(8, allProducts.size()));
                        System.out.println("ℹ️  Using regular products as best sellers: " + bestSellers.size());
                    }
                } catch (Exception e) {
                    System.err.println("⚠️  Warning loading all products: " + e.getMessage());
                }
            }
            
            // Thêm vào model
            model.addAttribute("hotProducts", hotProducts);
            model.addAttribute("bestSellers", bestSellers);
            
            long endTime = System.currentTimeMillis();
            System.out.println("⏱️  Home page loaded in: " + (endTime - startTime) + "ms");
            System.out.println("📊 Final counts - Hot: " + hotProducts.size() + ", Best: " + bestSellers.size());
            
        } catch (Exception e) {
            System.err.println("❌ CRITICAL ERROR in home page: " + e.getMessage());
            e.printStackTrace();
            
            // Fallback: Trả về empty lists
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