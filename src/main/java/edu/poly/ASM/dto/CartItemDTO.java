package edu.poly.ASM.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO {
    
    private Long id;
    
    @NotNull(message = "ID sản phẩm không được để trống")
    private Long productId;
    
    private String productName;
    private String productImage;
    private BigDecimal productPrice; // Giá gốc
    private BigDecimal productDiscountPrice; // Giá giảm
    private Integer productStock; // Tồn kho
    
    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 1, message = "Số lượng phải lớn hơn 0")
    private Integer soLuong = 1;
    
    // Calculated fields
    private BigDecimal giaCuoiCung; // Giá cuối cùng (sau giảm giá)
    private BigDecimal thanhTien; // Tổng tiền = giaCuoiCung * soLuong
    private boolean conHang;
    
    // Helper method để tính thành tiền
    public void calculateThanhTien() {
        if (giaCuoiCung != null && soLuong != null) {
            thanhTien = giaCuoiCung.multiply(BigDecimal.valueOf(soLuong));
        }
    }
}