package edu.poly.ASM.controller;

import edu.poly.ASM.entity.GioHang;
import edu.poly.ASM.service.GioHangService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {
    
    private static final Logger log = LoggerFactory.getLogger(CartController.class);
    
    @Autowired
    private GioHangService gioHangService;
    
    /**
     * 🛒 XEM GIỎ HÀNG
     */
    @GetMapping
    public String viewCart(Authentication auth, Model model) {
        if (auth == null) {
            return "redirect:/login";
        }
        
        String soDienThoai = auth.getName();
        
        try {
            List<GioHang> cartItems = gioHangService.getCartByUser(soDienThoai);
            Long total = gioHangService.calculateTotal(cartItems);
            
            model.addAttribute("pageTitle", "Giỏ hàng - Gaming Shop");
            model.addAttribute("cartItems", cartItems);
            model.addAttribute("total", total);
            model.addAttribute("cartCount", cartItems.size());
            
        } catch (Exception e) {
            log.error("Error loading cart", e);
            model.addAttribute("error", "Có lỗi khi tải giỏ hàng");
        }
        
        return "cart/view";
    }
    
    /**
     * ➕ THÊM VÀO GIỎ HÀNG
     */
    @PostMapping("/add")
    public String addToCart(
            @RequestParam Long productId,
            @RequestParam(defaultValue = "1") Integer quantity,
            Authentication auth,
            RedirectAttributes redirectAttributes) {
        
        if (auth == null) {
            return "redirect:/login";
        }
        
        try {
            String soDienThoai = auth.getName();
            gioHangService.addToCart(soDienThoai, productId, quantity);
            
            redirectAttributes.addFlashAttribute("successMessage", 
                "Đã thêm sản phẩm vào giỏ hàng!");
            
        } catch (Exception e) {
            log.error("Error adding to cart", e);
            redirectAttributes.addFlashAttribute("errorMessage", 
                "Có lỗi: " + e.getMessage());
        }
        
        return "redirect:/cart";
    }
    
    /**
     * 🔄 CẬP NHẬT SỐ LƯỢNG
     */
    @PostMapping("/update/{id}")
    public String updateQuantity(
            @PathVariable Long id,
            @RequestParam Integer quantity,
            RedirectAttributes redirectAttributes) {
        
        try {
            gioHangService.updateQuantity(id, quantity);
            redirectAttributes.addFlashAttribute("successMessage", 
                "Đã cập nhật số lượng!");
            
        } catch (Exception e) {
            log.error("Error updating cart", e);
            redirectAttributes.addFlashAttribute("errorMessage", 
                "Có lỗi: " + e.getMessage());
        }
        
        return "redirect:/cart";
    }
    
    /**
     * 🗑️ XÓA KHỎI GIỎ HÀNG
     */
    @GetMapping("/remove/{id}")
    public String removeFromCart(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {
        
        try {
            gioHangService.removeFromCart(id);
            redirectAttributes.addFlashAttribute("successMessage", 
                "Đã xóa sản phẩm khỏi giỏ hàng!");
            
        } catch (Exception e) {
            log.error("Error removing from cart", e);
            redirectAttributes.addFlashAttribute("errorMessage", 
                "Có lỗi khi xóa sản phẩm");
        }
        
        return "redirect:/cart";
    }
    
    /**
     * 🧹 XÓA TOÀN BỘ GIỎ HÀNG
     */
    @GetMapping("/clear")
    public String clearCart(
            Authentication auth,
            RedirectAttributes redirectAttributes) {
        
        if (auth == null) {
            return "redirect:/login";
        }
        
        try {
            String soDienThoai = auth.getName();
            gioHangService.clearCart(soDienThoai);
            redirectAttributes.addFlashAttribute("successMessage", 
                "Đã xóa toàn bộ giỏ hàng!");
            
        } catch (Exception e) {
            log.error("Error clearing cart", e);
            redirectAttributes.addFlashAttribute("errorMessage", 
                "Có lỗi khi xóa giỏ hàng");
        }
        
        return "redirect:/cart";
    }
    
    /**
     * 💳 CHUYỂN ĐẾN TRANG THANH TOÁN
     */
    @GetMapping("/checkout")
    public String checkout(Authentication auth) {
        if (auth == null) {
            return "redirect:/login";
        }
        return "redirect:/checkout";
    }
}