package edu.poly.ASM.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import edu.poly.ASM.entity.SanPham;
import edu.poly.ASM.service.SanPhamService;

@Controller
public class HomeController {
    
    private static final Logger log = LoggerFactory.getLogger(HomeController.class);
    
    private final SanPhamService sanPhamService;
    
    // Constructor injection
    @Autowired
    public HomeController(SanPhamService sanPhamService) {
        this.sanPhamService = sanPhamService;
    }
    
    @GetMapping("/")
    public String home(Model model) {
        log.info("Accessing home page");
        
        try {
            // Lấy sản phẩm mới nhất
            List<SanPham> latestProducts = sanPhamService.getLatestProducts(8);
            log.info("Found {} latest products", latestProducts.size());
            
            // Lấy sản phẩm đang giảm giá
            List<SanPham> saleProducts = sanPhamService.getProductsOnSale(8);
            log.info("Found {} sale products", saleProducts.size());
            
            // Lấy sản phẩm bán chạy
            List<SanPham> bestSellingProducts = sanPhamService.getBestSellingProducts(8);
            log.info("Found {} best selling products", bestSellingProducts.size());
            
            // Đếm tổng số sản phẩm
            long totalProducts = sanPhamService.countAllProducts();
            
            // Add vào model
            model.addAttribute("pageTitle", "Gaming Shop - Trang chủ");
            model.addAttribute("latestProducts", latestProducts);
            model.addAttribute("saleProducts", saleProducts);
            model.addAttribute("bestSellingProducts", bestSellingProducts);
            model.addAttribute("totalProducts", totalProducts);
            
        } catch (Exception e) {
            log.error("Error loading home page data", e);
            model.addAttribute("error", "Có lỗi xảy ra khi tải dữ liệu");
        }
        
        return "home";
    }
    
    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("pageTitle", "Về chúng tôi - Gaming Shop");
        return "about";
    }
}