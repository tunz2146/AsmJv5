package edu.poly.ASM.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "Chi_Tiet_Don_Hang")
public class ChiTietDonHang {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_don_hang", nullable = false)
    private DonHang donHang;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_san_pham", nullable = false)
    private SanPham sanPham;
    
    @Column(name = "gia", nullable = false)
    private Long gia;
    
    @Column(name = "khuyen_mai")
    private Integer khuyenMai;
    
    @Column(name = "so_luong", nullable = false)
    private Integer soLuong;
    
    // Constructors
    public ChiTietDonHang() {}
    
    public ChiTietDonHang(DonHang donHang, SanPham sanPham, Long gia, Integer soLuong) {
        this.donHang = donHang;
        this.sanPham = sanPham;
        this.gia = gia;
        this.soLuong = soLuong;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public DonHang getDonHang() {
        return donHang;
    }
    
    public void setDonHang(DonHang donHang) {
        this.donHang = donHang;
    }
    
    public SanPham getSanPham() {
        return sanPham;
    }
    
    public void setSanPham(SanPham sanPham) {
        this.sanPham = sanPham;
    }
    
    public Long getGia() {
        return gia;
    }
    
    public void setGia(Long gia) {
        this.gia = gia;
    }
    
    public Integer getKhuyenMai() {
        return khuyenMai;
    }
    
    public void setKhuyenMai(Integer khuyenMai) {
        this.khuyenMai = khuyenMai;
    }
    
    public Integer getSoLuong() {
        return soLuong;
    }
    
    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }
    
    // Transient method - tính tổng tiền
    @Transient
    public Long getTongTien() {
        long giaGoc = gia * soLuong;
        if (khuyenMai != null && khuyenMai > 0) {
            return giaGoc - (giaGoc * khuyenMai / 100);
        }
        return giaGoc;
    }
}