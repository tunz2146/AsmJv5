package edu.poly.ASM.controller.admin;

import edu.poly.ASM.repository.SanPhamRepository;
import edu.poly.ASM.repository.NguoiDungRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminDashboardController {

    @Autowired
    private SanPhamRepository sanPhamRepository;
    
    @Autowired
    private NguoiDungRepository nguoiDungRepository;

    /**
     * 📊 TRANG DASHBOARD ADMIN
     */
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // Thống kê cơ bản
        long totalProducts = sanPhamRepository.count();
        long totalUsers = nguoiDungRepository.count();
        
        model.addAttribute("pageTitle", "Admin Dashboard - Gaming Shop");
        model.addAttribute("totalProducts", totalProducts);
        model.addAttribute("totalUsers", totalUsers);
        model.addAttribute("totalOrders", 0); // Tạm thời
        model.addAttribute("totalRevenue", 0); // Tạm thời
        
        return "admin/dashboard";
    }

    // ❌ XÓA - Đã chuyển sang AdminProductController
    // products() đã được xử lý trong AdminProductController.java

    /**
     * 👥 QUẢN LÝ NGƯỜI DÙNG
     */
    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("pageTitle", "Quản lý người dùng");
        model.addAttribute("users", nguoiDungRepository.findAll());
        return "admin/user/list";
    }

    /**
     * 📋 QUẢN LÝ ĐỜN HÀNG
     */
    @GetMapping("/orders")
    public String orders(Model model) {
        model.addAttribute("pageTitle", "Quản lý đơn hàng");
        return "admin/order/list";
    }
}