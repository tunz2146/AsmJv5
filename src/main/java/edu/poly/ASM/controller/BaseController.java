package edu.poly.ASM.controller;

import edu.poly.ASM.service.GioHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * BASE CONTROLLER
 * Inject dữ liệu chung vào tất cả các trang
 */
@ControllerAdvice
public class BaseController {
    
    @Autowired
    private GioHangService gioHangService;
    
    /**
     * Thêm số lượng giỏ hàng vào model cho tất cả các trang
     */
    @ModelAttribute
    public void addCartCount(Model model, Authentication auth) {
        if (auth != null && auth.isAuthenticated()) {
            try {
                String username = auth.getName();
                Long cartCount = gioHangService.countCartItems(username);
                model.addAttribute("globalCartCount", cartCount);
            } catch (Exception e) {
                model.addAttribute("globalCartCount", 0L);
            }
        } else {
            model.addAttribute("globalCartCount", 0L);
        }
    }
}