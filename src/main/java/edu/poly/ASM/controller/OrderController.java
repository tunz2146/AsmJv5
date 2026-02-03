package edu.poly.ASM.controller;

import edu.poly.ASM.entity.DonHang;
import edu.poly.ASM.service.DonHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {
    
    @Autowired
    private DonHangService donHangService;
    
    /**
     * ✅ TRANG ĐẶT HÀNG THÀNH CÔNG
     */
    @GetMapping("/success")
    public String orderSuccess(Model model) {
        model.addAttribute("pageTitle", "Đặt hàng thành công - Gaming Shop");
        return "order/success";
    }
    
    /**
     * 📋 DANH SÁCH ĐƠN HÀNG CỦA USER
     */
    @GetMapping("s")
    public String orderHistory(Authentication auth, Model model) {
        if (auth == null) {
            return "redirect:/login";
        }
        
        String soDienThoai = auth.getName();
        
        try {
            List<DonHang> orders = donHangService.getOrdersByUser(soDienThoai);
            
            model.addAttribute("pageTitle", "Đơn hàng của tôi - Gaming Shop");
            model.addAttribute("orders", orders);
            
        } catch (Exception e) {
            model.addAttribute("error", "Có lỗi khi tải danh sách đơn hàng");
        }
        
        return "order/history";
    }
    
    /**
     * 👁️ CHI TIẾT ĐƠN HÀNG
     */
    @GetMapping("/detail/{id}")
    public String orderDetail(@PathVariable Long id, Model model) {
        // TODO: Implement order detail page
        model.addAttribute("pageTitle", "Chi tiết đơn hàng - Gaming Shop");
        return "order/detail";
    }
}