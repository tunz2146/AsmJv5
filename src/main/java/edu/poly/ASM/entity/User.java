package edu.poly.ASM.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Nguoi_Dung")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "so_dien_thoai", unique = true, nullable = false, length = 20)
    private String soDienThoai;
    
    @Column(name = "ten", nullable = false)
    private String ten;
    
    @Column(name = "password", nullable = false)
    private String password;
    
    @Column(name = "avatar", length = 500)
    private String avatar;
    
    @Column(name = "gioi_tinh")
    private Boolean gioiTinh; // 0: Nữ, 1: Nam
    
    @Column(name = "ngay_sinh")
    private LocalDate ngaySinh;
    
    @Column(name = "email", unique = true)
    private String email;
    
    @Column(name = "role", length = 50)
    private String role = "USER"; // USER, ADMIN
    
    @Column(name = "trang_thai")
    private Boolean trangThai = true; // 0: Khóa, 1: Hoạt động
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Quan hệ với các bảng khác
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addresses = new ArrayList<>();
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cart> cartItems = new ArrayList<>();
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Helper methods
    @Transient
    public String getGioiTinhText() {
        if (gioiTinh == null) return "Chưa xác định";
        return gioiTinh ? "Nam" : "Nữ";
    }
    
    @Transient
    public boolean isAdmin() {
        return "ADMIN".equalsIgnoreCase(role);
    }
    
    @Transient
    public boolean isActive() {
        return Boolean.TRUE.equals(trangThai);
    }
    
    // Lấy địa chỉ mặc định
    @Transient
    public Address getDiaChiMacDinh() {
        return addresses.stream()
            .filter(addr -> Boolean.TRUE.equals(addr.getMacDinh()))
            .findFirst()
            .orElse(null);
    }

    public String getDiaChiDayDu() {
        return String.format("%s, %s, %s, %s, %s", 
            diaChiChiTiet, phuongXa, quanHuyen, tinhThanh, "Việt Nam");
    }
    
    // Custom getters mapping English names to Vietnamese fields
    public String getUsername() {
        return this.soDienThoai;
    }
    
    public void setUsername(String username) {
        this.soDienThoai = username;
    }
    
    public Boolean getIsActive() {
        return this.trangThai;
    }
    
    public void setIsActive(Boolean isActive) {
        this.trangThai = isActive;
    }
    
    public String getFullName() {
        return this.ten;
    }
    
    public void setFullName(String fullName) {
        this.ten = fullName;
    }
    
    public Boolean getMacDinh() {
        return this.macDinh;
    }
    
    public Long getId() {
        return this.id;
    }
}