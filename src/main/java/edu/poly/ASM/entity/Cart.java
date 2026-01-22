package edu.poly.ASM.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Gio_Hang", 
       uniqueConstraints = @UniqueConstraint(columnNames = {"id_nguoi_dung", "id_san_pham"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_nguoi_dung", nullable = false)
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_san_pham", nullable = false)
    private Product product;
    
    @Column(name = "so_luong", nullable = false)
    private Integer soLuong = 1;
    
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
    
    // Tính tổng tiền của item trong giỏ hàng
    @Transient
    public BigDecimal getThanhTien() {
        if (product == null) return BigDecimal.ZERO;
        
        BigDecimal gia = product.getGiaGiam() != null ? 
                         product.getGiaGiam() : product.getGiaSanPham();
        
        return gia.multiply(BigDecimal.valueOf(soLuong));
    }
}