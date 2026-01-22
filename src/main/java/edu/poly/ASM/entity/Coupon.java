package edu.poly.ASM.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Khuyen_Mai")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Coupon {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "ma_khuyen_mai", unique = true, nullable = false, length = 50)
    private String maKhuyenMai;
    
    @Column(name = "ten", nullable = false)
    private String ten;
    
    @Column(name = "mo_ta", columnDefinition = "NVARCHAR(MAX)")
    private String moTa;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "loai_giam", length = 20)
    private LoaiGiam loaiGiam; // PERCENT, FIXED
    
    @Column(name = "gia_tri_giam", nullable = false)
    private BigDecimal giaTriGiam;
    
    @Column(name = "gia_tri_don_hang_toi_thieu")
    private BigDecimal giaTriDonHangToiThieu = BigDecimal.ZERO;
    
    @Column(name = "so_luong_ma")
    private Integer soLuongMa = 1;
    
    @Column(name = "da_su_dung")
    private Integer daSuDung = 0;
    
    @Column(name = "ngay_bat_dau", nullable = false)
    private LocalDateTime ngayBatDau;
    
    @Column(name = "ngay_ket_thuc", nullable = false)
    private LocalDateTime ngayKetThuc;
    
    @Column(name = "trang_thai")
    private Boolean trangThai = true;
    
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
    
    // Enum cho loại giảm giá
    public enum LoaiGiam {
        PERCENT, // Giảm theo %
        FIXED    // Giảm cố định
    }
    
    // Kiểm tra coupon còn hiệu lực
    @Transient
    public boolean isValid() {
        LocalDateTime now = LocalDateTime.now();
        return trangThai && 
               now.isAfter(ngayBatDau) && 
               now.isBefore(ngayKetThuc) &&
               daSuDung < soLuongMa;
    }
    
    // Tính số tiền giảm
    public BigDecimal tinhSoTienGiam(BigDecimal tongDonHang) {
        if (!isValid() || tongDonHang.compareTo(giaTriDonHangToiThieu) < 0) {
            return BigDecimal.ZERO;
        }
        
        if (loaiGiam == LoaiGiam.PERCENT) {
            return tongDonHang.multiply(giaTriGiam).divide(BigDecimal.valueOf(100));
        } else {
            return giaTriGiam;
        }
    }
}