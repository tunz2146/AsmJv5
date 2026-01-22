package edu.poly.ASM.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {
    
    private Long id;
    
    @NotNull(message = "ID sản phẩm không được để trống")
    private Long productId;
    
    private String productName;
    
    private Long userId;
    private String userName;
    private String userAvatar;
    
    @NotNull(message = "Điểm đánh giá không được để trống")
    @Min(value = 1, message = "Điểm đánh giá tối thiểu là 1 sao")
    @Max(value = 5, message = "Điểm đánh giá tối đa là 5 sao")
    private Integer diemDanhGia;
    
    @Size(max = 2000, message = "Nội dung đánh giá không được vượt quá 2000 ký tự")
    private String noiDung;
    
    private String hinhAnh; // JSON array of image URLs
    
    private LocalDateTime createdAt;
}