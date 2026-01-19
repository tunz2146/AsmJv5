package edu.poly.ASM.controller;

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
        // Lấy sản phẩm hot (đang giảm giá)
        model.addAttribute("hotProducts", productService.getHotProducts(8));
        
        // Lấy sản phẩm bán chạy
        model.addAttribute("bestSellers", productService.getBestSellers(8));
        
        model.addAttribute("pageTitle", "Trang chủ");
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
