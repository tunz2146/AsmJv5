package edu.poly.ASM.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Don_Hang")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "ma_don", unique = true, nullable = false, length = 50)
    private String maDon;
    
    @Column(name = "ngay_dat")
    private LocalDateTime ngayDat;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "tinh_trang", length = 50)
    private TinhTrang tinhTrang = TinhTrang.CHO_XAC_NHAN;
    
    @Column(name = "tong_tien", nullable = false)
    private BigDecimal tongTien;
    
    @Column(name = "ghi_chu", columnDefinition = "NVARCHAR(MAX)")
    private String ghiChu;
    
    @Column(name = "phuong_thuc_thanh_toan", length = 100)
    private String phuongThucThanhToan = "COD";
    
    @Column(name = "trang_thai_thanh_toan")
    private Boolean trangThaiThanhToan = false;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_nguoi_dung", nullable = false)
    private User user;
    
    // Thông tin người nhận (snapshot)
    @Column(name = "ten_nguoi_nhan", nullable = false)
    private String tenNguoiNhan;
    
    @Column(name = "so_dien_thoai_nhan", nullable = false, length = 20)
    private String soDienThoaiNhan;
    
    @Column(name = "dia_chi_giao_hang", nullable = false, columnDefinition = "NVARCHAR(MAX)")
    private String diaChiGiaoHang;
    
    // Thông tin vận chuyển
    @Column(name = "don_vi_van_chuyen")
    private String donViVanChuyen;
    
    @Column(name = "ma_van_don", length = 100)
    private String maVanDon;
    
    @Column(name = "phi_van_chuyen")
    private BigDecimal phiVanChuyen = BigDecimal.ZERO;
    
    @Column(name = "khuyen_mai_so_tien_giam")
    private BigDecimal khuyenMaiSoTienGiam = BigDecimal.ZERO;
    
    @Column(name = "ma_khuyen_mai", length = 50)
    private String maKhuyenMai;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Quan hệ với OrderDetail
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetail> orderDetails = new ArrayList<>();
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        ngayDat = LocalDateTime.now();
        
        if (maDon == null || maDon.isEmpty()) {
            maDon = generateOrderCode();
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Generate mã đơn hàng
    private String generateOrderCode() {
        return "DH" + System.currentTimeMillis();
    }
    
    // Enum trạng thái đơn hàng
    public enum TinhTrang {
        CHO_XAC_NHAN("Chờ xác nhận"),
        DA_XAC_NHAN("Đã xác nhận"),
        DANG_GIAO("Đang giao"),
        DA_GIAO("Đã giao"),
        DA_HUY("Đã hủy");
        
        private final String displayName;
        
        TinhTrang(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    // Tính tổng tiền thực tế phải trả
    @Transient
    public BigDecimal getTongThanhToan() {
        return tongTien
            .add(phiVanChuyen != null ? phiVanChuyen : BigDecimal.ZERO)
            .subtract(khuyenMaiSoTienGiam != null ? khuyenMaiSoTienGiam : BigDecimal.ZERO);
    }
}