package edu.poly.ASM.controller.admin;

import edu.poly.ASM.entity.SanPham;
import edu.poly.ASM.repository.SanPhamRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/products")
public class ProductController {

    private final SanPhamRepository sanPhamRepository;

    public ProductController(SanPhamRepository sanPhamRepository) {
        this.sanPhamRepository = sanPhamRepository;
    }

    // 1️⃣ Danh sách sản phẩm
    @GetMapping
    public String list(Model model) {
        model.addAttribute("products", sanPhamRepository.findAll());
        return "admin/products/index";
    }

    // 2️⃣ Form thêm sản phẩm
    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("product", new SanPham());
        return "admin/products/create";
    }

    // 3️⃣ Lưu sản phẩm (thêm mới)
    @PostMapping("/store")
    public String store(@ModelAttribute("product") SanPham sanPham) {
        sanPhamRepository.save(sanPham);
        return "redirect:/admin/products";
    }

    // 4️⃣ Form sửa sản phẩm
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        SanPham sanPham = sanPhamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));
        model.addAttribute("product", sanPham);
        return "admin/products/edit";
    }

    // 5️⃣ Cập nhật sản phẩm
    @PostMapping("/update")
    public String update(@ModelAttribute("product") SanPham sanPham) {
        sanPhamRepository.save(sanPham);
        return "redirect:/admin/products";
    }

    // 6️⃣ Xóa sản phẩm
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        sanPhamRepository.deleteById(id);
        return "redirect:/admin/products";
    }
}
