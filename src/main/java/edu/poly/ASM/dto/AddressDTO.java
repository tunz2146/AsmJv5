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
public class AddressDTO {
    
    private Long id;
    
    @NotBlank(message = "Tên người nhận không được để trống")
    @Size(min = 2, max = 255, message = "Tên người nhận từ 2-255 ký tự")
    private String tenNguoiNhan;
    
    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^(0|\\+84)(\\s|\\.)?((3[2-9])|(5[689])|(7[06-9])|(8[1-689])|(9[0-46-9]))(\\d)(\\s|\\.)?(\\d{3})(\\s|\\.)?(\\d{3})$", 
             message = "Số điện thoại không hợp lệ")
    private String soDienThoai;
    
    @NotBlank(message = "Tỉnh/Thành phố không được để trống")
    private String tinhThanh;
    
    @NotBlank(message = "Quận/Huyện không được để trống")
    private String quanHuyen;
    
    @NotBlank(message = "Phường/Xã không được để trống")
    private String phuongXa;
    
    @NotBlank(message = "Địa chỉ chi tiết không được để trống")
    @Size(max = 500, message = "Địa chỉ chi tiết không được vượt quá 500 ký tự")
    private String diaChiChiTiet;
    
    private Boolean macDinh = false;
    
    private Long userId; // ID của user (dùng khi admin quản lý)
    
    // Transient field để hiển thị
    private String diaChiDayDu;
}