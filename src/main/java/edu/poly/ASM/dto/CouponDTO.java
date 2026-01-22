package edu.poly.ASM.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CouponDTO {
    
    private Long id;
    
    @NotBlank(message = "Mã khuyến mãi không được để trống")
    @Size(max = 50, message = "Mã khuyến mãi không được vượt quá 50 ký tự")
    private String maKhuyenMai;
    
    @NotBlank(message = "Tên khuyến mãi không được để trống")
    private String ten;
    
    private String moTa;
    
    @NotNull(message = "Loại giảm không được để trống")
    private String loaiGiam; // PERCENT, FIXED
    
    @NotNull(message = "Giá trị giảm không được để trống")
    @DecimalMin(value = "0.0", inclusive = false, message = "Giá trị giảm phải lớn hơn 0")
    private BigDecimal giaTriGiam;
    
    @DecimalMin(value = "0.0", message = "Giá trị đơn hàng tối thiểu không được âm")
    private BigDecimal giaTriDonHangToiThieu = BigDecimal.ZERO;
    
    @Min(value = 1, message = "Số lượng mã phải lớn hơn 0")
    private Integer soLuongMa = 1;
    
    private Integer daSuDung = 0;
    
    @NotNull(message = "Ngày bắt đầu không được để trống")
    private LocalDateTime ngayBatDau;
    
    @NotNull(message = "Ngày kết thúc không được để trống")
    private LocalDateTime ngayKetThuc;
    
    private Boolean trangThai = true;
    
    // Transient
    private boolean valid; // Còn hiệu lực hay không
    private Integer soLuongConLai; // Số lượng mã còn lại
}