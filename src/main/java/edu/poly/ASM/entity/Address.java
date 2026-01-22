package edu.poly.ASM.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "Dia_Chi")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_nguoi_dung", nullable = false)
    private User user;
    
    @Column(name = "ten_nguoi_nhan", nullable = false)
    private String tenNguoiNhan;
    
    @Column(name = "so_dien_thoai", nullable = false, length = 20)
    private String soDienThoai;
    
    @Column(name = "tinh_thanh", nullable = false, length = 100)
    private String tinhThanh;
    
    @Column(name = "quan_huyen", nullable = false, length = 100)
    private String quanHuyen;
    
    @Column(name = "phuong_xa", nullable = false, length = 100)
    private String phuongXa;
    
    @Column(name = "dia_chi_chi_tiet", nullable = false, length = 500)
    private String diaChiChiTiet;
    
    @Column(name = "mac_dinh")
    private Boolean macDinh = false;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Helper method để lấy địa chỉ đầy đủ
    public String getDiaChiDayDu() {
        return String.format("%s, %s, %s, %s, %s", 
            diaChiChiTiet, phuongXa, quanHuyen, tinhThanh, "Việt Nam");
    }
}