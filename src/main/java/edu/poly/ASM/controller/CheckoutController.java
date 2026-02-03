package edu.poly.ASM.controller;

import edu.poly.ASM.entity.DonHang;
import edu.poly.ASM.entity.GioHang;
import edu.poly.ASM.entity.NguoiDung;
import edu.poly.ASM.repository.NguoiDungRepository;
import edu.poly.ASM.service.DonHangService;
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
@RequestMapping("/checkout")
public class CheckoutController {
    
    private static final Logger log = LoggerFactory.getLogger(CheckoutController.class);
    
    @Autowired
    private GioHangService gioHangService;
    
    @Autowired
    private DonHangService donHangService;
    
    @Autowired
    private NguoiDungRepository nguoiDungRepository;
    
    /**
     * 💳 TRANG THANH TOÁN
     */
    @GetMapping
    public String checkout(Authentication auth, Model model) {
        if (auth == null) {
            return "redirect:/login";
        }
        
        String soDienThoai = auth.getName();
        
        try {
            // Lấy giỏ hàng
            List<GioHang> cartItems = gioHangService.getCartByUser(soDienThoai);
            
            if (cartItems.isEmpty()) {
                return "redirect:/cart";
            }
            
            Long total = gioHangService.calculateTotal(cartItems);
            
            // Lấy thông tin user
            NguoiDung nguoiDung = nguoiDungRepository.findBySoDienThoai(soDienThoai)
                    .orElse(null);
            
            model.addAttribute("pageTitle", "Thanh toán - Gaming Shop");
            model.addAttribute("cartItems", cartItems);
            model.addAttribute("total", total);
            model.addAttribute("user", nguoiDung);
            
        } catch (Exception e) {
            log.error("Error loading checkout", e);
            model.addAttribute("error", "Có lỗi khi tải trang thanh toán");
        }
        
        return "cart/checkout";
    }
    
    /**
     * 📦 XỬ LÝ ĐẶT HÀNG
     */
    @PostMapping("/place-order")
    public String placeOrder(
            @RequestParam String diaChiGiaoHang,
            @RequestParam String tenNguoiNhan,
            @RequestParam String sdtNguoiNhan,
            @RequestParam(required = false) String ghiChu,
            Authentication auth,
            RedirectAttributes redirectAttributes) {
        
        if (auth == null) {
            return "redirect:/login";
        }
        
        try {
            String soDienThoai = auth.getName();
            
            // Tạo đơn hàng
            DonHang donHang = donHangService.createOrder(
                soDienThoai, 
                diaChiGiaoHang, 
                tenNguoiNhan, 
                sdtNguoiNhan
            );
            
            redirectAttributes.addFlashAttribute("successMessage", 
                "Đặt hàng thành công! Mã đơn hàng: #" + donHang.getId());
            
            return "redirect:/order/success";
            
        } catch (Exception e) {
            log.error("Error placing order", e);
            redirectAttributes.addFlashAttribute("errorMessage", 
                "Có lỗi khi đặt hàng: " + e.getMessage());
            return "redirect:/checkout";
        }
    }
}