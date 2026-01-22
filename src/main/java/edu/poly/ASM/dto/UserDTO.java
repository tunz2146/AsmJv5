package edu.poly.ASM.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    
    private Long id;
    
    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^(0|\\+84)(\\s|\\.)?((3[2-9])|(5[689])|(7[06-9])|(8[1-689])|(9[0-46-9]))(\\d)(\\s|\\.)?(\\d{3})(\\s|\\.)?(\\d{3})$", 
             message = "Số điện thoại không hợp lệ")
    private String soDienThoai;
    
    @NotBlank(message = "Tên không được để trống")
    @Size(min = 2, max = 255, message = "Tên phải từ 2-255 ký tự")
    private String ten;
    
    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 6, message = "Mật khẩu phải có ít nhất 6 ký tự")
    private String password;
    
    private String confirmPassword; // Chỉ dùng khi đăng ký/đổi mật khẩu
    
    private String avatar;
    
    private Boolean gioiTinh; // true: Nam, false: Nữ
    
    @Past(message = "Ngày sinh phải là ngày trong quá khứ")
    private LocalDate ngaySinh;
    
    @Email(message = "Email không hợp lệ")
    private String email;
    
    private String role = "USER";
    
    private Boolean trangThai = true;
}