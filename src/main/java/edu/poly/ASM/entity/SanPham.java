package edu.poly.ASM.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "San_Pham")
public class SanPham {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "ten_san_pham", nullable = false, length = 255)
    private String tenSanPham;
    
    @Column(name = "gia_san_pham", nullable = false)
    private Long giaSanPham;
    
    @Column(name = "chiet_khau")
    private Integer chietKhau;
    
    @Column(name = "thue")
    private Integer thue;
    
    @Column(name = "ton_kho")
    private Integer tonKho;
    
    @Column(name = "hinh_anh", length = 255)
    private String hinhAnh;
    
    @Column(name = "thong_so", columnDefinition = "NVARCHAR(MAX)")
    private String thongSo;
    
    @Column(name = "slug", length = 255)
    private String slug;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_hang")
    private Hang hang;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "Product_Category",
        joinColumns = @JoinColumn(name = "id_san_pham"),
        inverseJoinColumns = @JoinColumn(name = "id_loai_san_pham")
    )
    private List<LoaiSanPham> loaiSanPhams;
    
    @OneToMany(mappedBy = "sanPham", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ChiTietDonHang> chiTietDonHangs;
    
    @OneToMany(mappedBy = "sanPham", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<GioHang> gioHangs;
    
    @OneToMany(mappedBy = "sanPham", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DanhGia> danhGias;
    
    // Constructors
    public SanPham() {}
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTenSanPham() {
        return tenSanPham;
    }
    
    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }
    
    public Long getGiaSanPham() {
        return giaSanPham;
    }
    
    public void setGiaSanPham(Long giaSanPham) {
        this.giaSanPham = giaSanPham;
    }
    
    public Integer getChietKhau() {
        return chietKhau;
    }
    
    public void setChietKhau(Integer chietKhau) {
        this.chietKhau = chietKhau;
    }
    
    public Integer getTonKho() {
        return tonKho;
    }
    
    public void setTonKho(Integer tonKho) {
        this.tonKho = tonKho;
    }
    
    public String getHinhAnh() {
        return hinhAnh;
    }
    
    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }
    
    public String getSlug() {
        return slug;
    }
    
    public void setSlug(String slug) {
        this.slug = slug;
    }
    
    public Hang getHang() {
        return hang;
    }
    
    public void setHang(Hang hang) {
        this.hang = hang;
    }
    
    public List<LoaiSanPham> getLoaiSanPhams() {
        return loaiSanPhams;
    }
    
    public void setLoaiSanPhams(List<LoaiSanPham> loaiSanPhams) {
        this.loaiSanPhams = loaiSanPhams;
    }
    
    // Transient methods
    @Transient
    public Long getGiaSauGiam() {
        if (chietKhau != null && chietKhau > 0) {
            return giaSanPham - (giaSanPham * chietKhau / 100);
        }
        return giaSanPham;
    }
    
    @Transient
    public boolean isOnSale() {
        return chietKhau != null && chietKhau > 0;
    }
    
    @Transient
    public boolean isInStock() {
        return tonKho != null && tonKho > 0;
    }
}