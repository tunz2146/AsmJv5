package edu.poly.ASM.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    
    private Long id;
    private String maDon;
    private LocalDateTime ngayDat;
    private String tinhTrang; // CHO_XAC_NHAN, DA_XAC_NHAN, DANG_GIAO, DA_GIAO, DA_HUY
    
    @NotNull(message = "Tổng tiền không được để trống")
    private BigDecimal tongTien;
    
    private String ghiChu;
    
    @NotBlank(message = "Phương thức thanh toán không được để trống")
    private String phuongThucThanhToan = "COD";
    
    private Boolean trangThaiThanhToan = false;
    
    // Thông tin người nhận
    @NotBlank(message = "Tên người nhận không được để trống")
    private String tenNguoiNhan;
    
    @NotBlank(message = "Số điện thoại người nhận không được để trống")
    @Pattern(regexp = "^(0|\\+84)(\\s|\\.)?((3[2-9])|(5[689])|(7[06-9])|(8[1-689])|(9[0-46-9]))(\\d)(\\s|\\.)?(\\d{3})(\\s|\\.)?(\\d{3})$")
    private String soDienThoaiNhan;
    
    @NotBlank(message = "Địa chỉ giao hàng không được để trống")
    private String diaChiGiaoHang;
    
    // Thông tin vận chuyển
    private String donViVanChuyen;
    private String maVanDon;
    private BigDecimal phiVanChuyen = BigDecimal.ZERO;
    
    // Khuyến mãi
    private BigDecimal khuyenMaiSoTienGiam = BigDecimal.ZERO;
    private String maKhuyenMai;
    
    // Chi tiết đơn hàng
    private List<OrderDetailDTO> orderDetails = new ArrayList<>();
    
    // User info
    private Long userId;
    private String userName;
    
    // Calculated
    private BigDecimal tongThanhToan; // tongTien + phiVanChuyen - khuyenMaiSoTienGiam
}