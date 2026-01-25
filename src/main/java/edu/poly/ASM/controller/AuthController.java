package edu.poly.ASM.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {
    
    /**
     * Hiển thị trang đăng nhập
     */
    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("pageTitle", "Đăng nhập - Gaming Shop");
        return "auth/login";
    }
    
    /**
     * Hiển thị trang đăng ký (tạm thời)
     */
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("pageTitle", "Đăng ký - Gaming Shop");
        // Sẽ tạo trang register sau
        return "auth/register";
    }
    
    /**
     * Trang quên mật khẩu (tạm thời)
     */
    @GetMapping("/forgot-password")
    public String forgotPassword(Model model) {
        model.addAttribute("pageTitle", "Quên mật khẩu - Gaming Shop");
        return "auth/forgot-password";
    }
}