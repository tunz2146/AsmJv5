package edu.poly.ASM.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "San_Pham")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "ten_san_pham", nullable = false, length = 500)
    private String tenSanPham;
    
    @Column(name = "slug", unique = true, length = 500)
    private String slug;
    
    @Column(name = "gia_san_pham", nullable = false)
    private BigDecimal giaSanPham;
    
    @Column(name = "gia_giam")
    private BigDecimal giaGiam; // Giá sau khuyến mãi
    
    @Column(name = "chiet_khau")
    private Integer chietKhau = 0; // % chiết khấu
    
    @Column(name = "thue")
    private Integer thue = 0; // % thuế VAT
    
    @Column(name = "ton_kho")
    private Integer tonKho = 0;
    
    @Column(name = "hinh_anh", length = 500)
    private String hinhAnh;
    
    @Column(name = "danh_sach_hinh_anh", columnDefinition = "NVARCHAR(MAX)")
    private String danhSachHinhAnh; // JSON array
    
    @Column(name = "mo_ta_ngan", length = 1000)
    private String moTaNgan;
    
    @Column(name = "mo_ta_chi_tiet", columnDefinition = "NVARCHAR(MAX)")
    private String moTaChiTiet;
    
    @Column(name = "thong_so_ky_thuat", columnDefinition = "NVARCHAR(MAX)")
    private String thongSoKyThuat; // JSON specifications
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_hang")
    private Brand brand;
    
    @Column(name = "luot_xem")
    private Integer luotXem = 0;
    
    @Column(name = "danh_gia_trung_binh", precision = 3, scale = 2)
    private BigDecimal danhGiaTrungBinh = BigDecimal.ZERO;
    
    @Column(name = "trang_thai")
    private Boolean trangThai = true; // 0: Ẩn, 1: Hiển thị
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Many-to-Many với Category
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "Product_Category",
        joinColumns = @JoinColumn(name = "id_san_pham"),
        inverseJoinColumns = @JoinColumn(name = "id_loai_san_pham")
    )
    private Set<Category> categories = new HashSet<>();
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        
        if (slug == null || slug.isEmpty()) {
            slug = createSlug(tenSanPham);
        }
        
        // Tính chiết khấu nếu có giá giảm
        if (giaGiam != null && giaGiam.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal discount = giaSanPham.subtract(giaGiam);
            chietKhau = discount.multiply(BigDecimal.valueOf(100))
                               .divide(giaSanPham, 0, java.math.RoundingMode.HALF_UP)
                               .intValue();
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    private String createSlug(String text) {
        if (text == null) return "";
        return text.toLowerCase()
                   .replaceAll("[àáạảãâầấậẩẫăằắặẳẵ]", "a")
                   .replaceAll("[èéẹẻẽêềếệểễ]", "e")
                   .replaceAll("[ìíịỉĩ]", "i")
                   .replaceAll("[òóọỏõôồốộổỗơờớợởỡ]", "o")
                   .replaceAll("[ùúụủũưừứựửữ]", "u")
                   .replaceAll("[ỳýỵỷỹ]", "y")
                   .replaceAll("[đ]", "d")
                   .replaceAll("[^a-z0-9\\s-]", "")
                   .trim()
                   .replaceAll("\\s+", "-");
    }
    
    // Lấy giá cuối cùng
    @Transient
    public BigDecimal getGiaCuoiCung() {
        return (giaGiam != null && giaGiam.compareTo(BigDecimal.ZERO) > 0) ? 
               giaGiam : giaSanPham;
    }

    @Transient
    public BigDecimal getFinalPrice() {
        return getGiaCuoiCung();
    }

    
    // Kiểm tra còn hàng
    @Transient
    public boolean isConHang() {
        return tonKho != null && tonKho > 0;
    }
    
    // Tính % giảm giá
    @Transient
    public Integer getPhanTramGiam() {
        if (giaGiam == null || giaGiam.compareTo(BigDecimal.ZERO) <= 0) {
            return 0;
        }
        BigDecimal discount = giaSanPham.subtract(giaGiam);
        return discount.multiply(BigDecimal.valueOf(100))
                      .divide(giaSanPham, 0, java.math.RoundingMode.HALF_UP)
                      .intValue();
    }
    
    // Custom getters mapping English names to Vietnamese fields
    public String getName() {
        return this.tenSanPham;
    }
    
    public void setName(String name) {
        this.tenSanPham = name;
    }
    
    public String getDescription() {
        return this.moTaNgan;
    }
    
    public void setDescription(String description) {
        this.moTaNgan = description;
    }
    
    public BigDecimal getPrice() {
        return this.giaSanPham;
    }
    
    public void setPrice(BigDecimal price) {
        this.giaSanPham = price;
    }
    
    public BigDecimal getDiscountPrice() {
        return this.giaGiam;
    }
    
    public void setDiscountPrice(BigDecimal discountPrice) {
        this.giaGiam = discountPrice;
    }
    
    public Integer getStockQuantity() {
        return this.tonKho;
    }
    
    public void setStockQuantity(Integer stockQuantity) {
        this.tonKho = stockQuantity;
    }
    
    public String getImageUrl() {
        return this.hinhAnh;
    }
    
    public void setImageUrl(String imageUrl) {
        this.hinhAnh = imageUrl;
    }
    
    public BigDecimal getGiaSanPham() {
        return this.giaSanPham;
    }
    
    public void setGiaSanPham(BigDecimal giaSanPham) {
        this.giaSanPham = giaSanPham;
    }
    
    public BigDecimal getGiaGiam() {
        return this.giaGiam;
    }
    
    public void setGiaGiam(BigDecimal giaGiam) {
        this.giaGiam = giaGiam;
    }
    
    public Boolean getIsActive() {
        return this.trangThai;
    }
    
    public void setIsActive(Boolean isActive) {
        this.trangThai = isActive;
    }
    
    public Set<Category> getCategory() {
        return this.categories;
    }
    
    public void setCategory(Set<Category> categories) {
        this.categories = categories;
    }
    
    // Helper method to get first category
    public Category getFirstCategory() {
        return this.categories != null && !this.categories.isEmpty() 
            ? this.categories.iterator().next() 
            : null;
    }
}