package edu.poly.ASM.controller.admin;

import edu.poly.ASM.entity.SanPham;
import edu.poly.ASM.repository.SanPhamRepository;
import edu.poly.ASM.repository.HangRepository;
import edu.poly.ASM.repository.LoaiSanPhamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/products")
public class AdminProductController {

    @Autowired
    private SanPhamRepository sanPhamRepository;
    
    @Autowired
    private HangRepository hangRepository;
    
    @Autowired
    private LoaiSanPhamRepository loaiSanPhamRepository;

    /**
     * 📋 DANH SÁCH SẢN PHẨM
     */
    @GetMapping
    public String list(Model model) {
        model.addAttribute("pageTitle", "Quản lý sản phẩm");
        model.addAttribute("products", sanPhamRepository.findAll());
        return "admin/product/list";
    }

    /**
     * ➕ FORM THÊM SẢN PHẨM
     */
    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("pageTitle", "Thêm sản phẩm mới");
        model.addAttribute("product", new SanPham());
        model.addAttribute("brands", hangRepository.findAll());
        model.addAttribute("categories", loaiSanPhamRepository.findAll());
        return "admin/product/form";
    }

    /**
     * 💾 LƯU SẢN PHẨM MỚI
     */
    @PostMapping("/store")
    public String store(@ModelAttribute("product") SanPham sanPham, 
                       RedirectAttributes redirectAttributes) {
        try {
            // Tạo slug tự động từ tên sản phẩm
            if (sanPham.getSlug() == null || sanPham.getSlug().isEmpty()) {
                sanPham.setSlug(createSlug(sanPham.getTenSanPham()));
            }
            
            sanPhamRepository.save(sanPham);
            
            redirectAttributes.addFlashAttribute("successMessage", 
                "Thêm sản phẩm thành công!");
            return "redirect:/admin/products";
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", 
                "Có lỗi xảy ra: " + e.getMessage());
            return "redirect:/admin/products/create";
        }
    }

    /**
     * 👁️ XEM CHI TIẾT SẢN PHẨM
     */
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, Model model) {
        SanPham sanPham = sanPhamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));
        
        model.addAttribute("pageTitle", "Chi tiết: " + sanPham.getTenSanPham());
        model.addAttribute("product", sanPham);
        return "admin/product/detail";
    }

    /**
     * ✏️ FORM SỬA SẢN PHẨM
     */
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        SanPham sanPham = sanPhamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));
        
        model.addAttribute("pageTitle", "Sửa sản phẩm: " + sanPham.getTenSanPham());
        model.addAttribute("product", sanPham);
        model.addAttribute("brands", hangRepository.findAll());
        model.addAttribute("categories", loaiSanPhamRepository.findAll());
        return "admin/product/form";
    }

    /**
     * 🔄 CẬP NHẬT SẢN PHẨM
     */
    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id,
                        @ModelAttribute("product") SanPham sanPham,
                        RedirectAttributes redirectAttributes) {
        try {
            sanPham.setId(id);
            
            // Cập nhật slug nếu cần
            if (sanPham.getSlug() == null || sanPham.getSlug().isEmpty()) {
                sanPham.setSlug(createSlug(sanPham.getTenSanPham()));
            }
            
            sanPhamRepository.save(sanPham);
            
            redirectAttributes.addFlashAttribute("successMessage", 
                "Cập nhật sản phẩm thành công!");
            return "redirect:/admin/products";
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", 
                "Có lỗi xảy ra: " + e.getMessage());
            return "redirect:/admin/products/edit/" + id;
        }
    }

    /**
     * 🗑️ XÓA SẢN PHẨM
     */
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, 
                        RedirectAttributes redirectAttributes) {
        try {
            SanPham sanPham = sanPhamRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));
            
            sanPhamRepository.deleteById(id);
            
            redirectAttributes.addFlashAttribute("successMessage", 
                "Xóa sản phẩm thành công!");
                
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", 
                "Không thể xóa sản phẩm: " + e.getMessage());
        }
        
        return "redirect:/admin/products";
    }

    /**
     * 🔧 TẠO SLUG TỰ ĐỘNG
     */
    private String createSlug(String text) {
        if (text == null || text.isEmpty()) {
            return "";
        }
        
        // Chuyển thành chữ thường
        String slug = text.toLowerCase();
        
        // Xóa dấu tiếng Việt
        slug = slug.replaceAll("[àáạảãâầấậẩẫăằắặẳẵ]", "a");
        slug = slug.replaceAll("[èéẹẻẽêềếệểễ]", "e");
        slug = slug.replaceAll("[ìíịỉĩ]", "i");
        slug = slug.replaceAll("[òóọỏõôồốộổỗơờớợởỡ]", "o");
        slug = slug.replaceAll("[ùúụủũưừứựửữ]", "u");
        slug = slug.replaceAll("[ỳýỵỷỹ]", "y");
        slug = slug.replaceAll("đ", "d");
        
        // Xóa ký tự đặc biệt, chỉ giữ chữ, số, dấu gạch ngang
        slug = slug.replaceAll("[^a-z0-9\\s-]", "");
        
        // Thay khoảng trắng bằng dấu gạch ngang
        slug = slug.replaceAll("\\s+", "-");
        
        // Xóa dấu gạch ngang thừa
        slug = slug.replaceAll("-+", "-");
        slug = slug.replaceAll("^-|-$", "");
        
        return slug;
    }
}