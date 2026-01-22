package edu.poly.ASM.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Chi_Tiet_Don_Hang")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_don_hang", nullable = false)
    private Order order;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_san_pham", nullable = false)
    private Product product;
    
    @Column(name = "gia_luc_chot", nullable = false)
    private BigDecimal giaLucChot; // Giá tại thời điểm đặt hàng
    
    @Column(name = "so_luong", nullable = false)
    private Integer soLuong;
    
    @Column(name = "thanh_tien", nullable = false)
    private BigDecimal thanhTien; // gia_luc_chot * so_luong
    
    @Column(name = "khuyen_mai_luc_chot")
    private Integer khuyenMaiLucChot = 0; // % chiết khấu lúc đặt
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        
        // Tự động tính thành tiền
        if (thanhTien == null && giaLucChot != null && soLuong != null) {
            thanhTien = giaLucChot.multiply(BigDecimal.valueOf(soLuong));
        }
    }
    
    // Helper method tính thành tiền
    public void calculateThanhTien() {
        if (giaLucChot != null && soLuong != null) {
            thanhTien = giaLucChot.multiply(BigDecimal.valueOf(soLuong));
        }
    }
}