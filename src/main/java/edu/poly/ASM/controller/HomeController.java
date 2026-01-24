package edu.poly.ASM.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    
    @GetMapping("/")
    public String home(Model model) {
        // Tạm thời chỉ hiển thị trang chủ đơn giản
        model.addAttribute("pageTitle", "Gaming Shop - Trang chủ");
        return "home";
    }
    
    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("pageTitle", "Về chúng tôi");
        return "about";
    }
}