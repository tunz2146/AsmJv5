package edu.poly.ASM.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailDTO {
    
    private Long id;
    
    @NotNull(message = "ID sản phẩm không được để trống")
    private Long productId;
    
    private String productName;
    private String productImage;
    
    @NotNull(message = "Giá lúc chốt không được để trống")
    @DecimalMin(value = "0.0", inclusive = false, message = "Giá phải lớn hơn 0")
    private BigDecimal giaLucChot;
    
    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 1, message = "Số lượng phải lớn hơn 0")
    private Integer soLuong;
    
    private BigDecimal thanhTien;
    
    @Min(value = 0, message = "Khuyến mãi không được âm")
    @Max(value = 100, message = "Khuyến mãi không được vượt quá 100%")
    private Integer khuyenMaiLucChot = 0;
}