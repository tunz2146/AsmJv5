package edu.poly.ASM.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/auth")
public class AuthController {
    
    /**
     * Trang đăng nhập
     */
    @GetMapping("/login")
    public String login(Model model, @RequestParam(required = false) String error) {
        model.addAttribute("pageTitle", "Đăng nhập");
        if (error != null) {
            model.addAttribute("error", "Tên người dùng hoặc mật khẩu không đúng");
        }
        return "auth/login";
    }
    
    /**
     * Trang đăng ký
     */
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("pageTitle", "Đăng ký");
        return "auth/register";
    }
}
