package edu.poly.ASM.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BrandDTO {
    
    private Long id;
    
    @NotBlank(message = "Tên hãng không được để trống")
    @Size(max = 255, message = "Tên hãng không được vượt quá 255 ký tự")
    private String tenHang;
    
    private String slug;
    
    private String logo;
    
    private String moTa;
    
    // Transient
    private Integer soLuongSanPham; // Số lượng sản phẩm của hãng
}