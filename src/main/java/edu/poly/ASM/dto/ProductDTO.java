package edu.poly.ASM.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    
    private Long id;
    
    @NotBlank(message = "Tên sản phẩm không được để trống")
    @Size(max = 500, message = "Tên sản phẩm không được vượt quá 500 ký tự")
    private String tenSanPham;
    
    private String slug;
    
    @NotNull(message = "Giá sản phẩm không được để trống")
    @DecimalMin(value = "0.0", inclusive = false, message = "Giá sản phẩm phải lớn hơn 0")
    private BigDecimal giaSanPham;
    
    @DecimalMin(value = "0.0", inclusive = false, message = "Giá giảm phải lớn hơn 0")
    private BigDecimal giaGiam;
    
    @Min(value = 0, message = "Chiết khấu không được âm")
    @Max(value = 100, message = "Chiết khấu không được vượt quá 100%")
    private Integer chietKhau = 0;
    
    @Min(value = 0, message = "Thuế không được âm")
    @Max(value = 100, message = "Thuế không được vượt quá 100%")
    private Integer thue = 0;
    
    @NotNull(message = "Tồn kho không được để trống")
    @Min(value = 0, message = "Tồn kho không được âm")
    private Integer tonKho = 0;
    
    private String hinhAnh;
    
    private String danhSachHinhAnh; // JSON array
    
    @Size(max = 1000, message = "Mô tả ngắn không được vượt quá 1000 ký tự")
    private String moTaNgan;
    
    private String moTaChiTiet;
    
    private String thongSoKyThuat; // JSON
    
    private Long brandId; // ID của hãng
    private String brandName; // Tên hãng (để hiển thị)
    
    private Set<Long> categoryIds; // Danh sách ID các danh mục
    
    private Integer luotXem = 0;
    
    private BigDecimal danhGiaTrungBinh = BigDecimal.ZERO;
    
    private Boolean trangThai = true;
    
    // Transient fields để hiển thị
    private String giaCuoiCungFormatted; // Giá đã format
    private boolean conHang;
    private Integer phanTramGiam;
}