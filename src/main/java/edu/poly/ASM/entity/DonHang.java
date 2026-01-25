package edu.poly.ASM.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Don_Hang")
public class DonHang {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "ngay_dat")
    private LocalDate ngayDat;
    
    @Column(name = "tinh_trang", length = 100)
    private String tinhTrang;
    
    @Column(name = "tong_tien")
    private Long tongTien;
    
    @Column(name = "thong_tin_giao_hang", columnDefinition = "NVARCHAR(MAX)")
    private String thongTinGiaoHang;
    
    @Column(name = "don_vi_van_chuyen", length = 255)
    private String donViVanChuyen;
    
    @Column(name = "khuyen_mai")
    private Long khuyenMai;
    
    @Column(name = "phi_van_chuyen")
    private Long phiVanChuyen;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_nguoi_dung", nullable = false)
    private NguoiDung nguoiDung;
    
    @OneToMany(mappedBy = "donHang", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ChiTietDonHang> chiTietDonHangs;
    
    // Constructors
    public DonHang() {}
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public LocalDate getNgayDat() {
        return ngayDat;
    }
    
    public void setNgayDat(LocalDate ngayDat) {
        this.ngayDat = ngayDat;
    }
    
    public String getTinhTrang() {
        return tinhTrang;
    }
    
    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }
    
    public Long getTongTien() {
        return tongTien;
    }
    
    public void setTongTien(Long tongTien) {
        this.tongTien = tongTien;
    }
    
    public String getThongTinGiaoHang() {
        return thongTinGiaoHang;
    }
    
    public void setThongTinGiaoHang(String thongTinGiaoHang) {
        this.thongTinGiaoHang = thongTinGiaoHang;
    }
    
    public String getDonViVanChuyen() {
        return donViVanChuyen;
    }
    
    public void setDonViVanChuyen(String donViVanChuyen) {
        this.donViVanChuyen = donViVanChuyen;
    }
    
    public Long getKhuyenMai() {
        return khuyenMai;
    }
    
    public void setKhuyenMai(Long khuyenMai) {
        this.khuyenMai = khuyenMai;
    }
    
    public Long getPhiVanChuyen() {
        return phiVanChuyen;
    }
    
    public void setPhiVanChuyen(Long phiVanChuyen) {
        this.phiVanChuyen = phiVanChuyen;
    }
    
    public NguoiDung getNguoiDung() {
        return nguoiDung;
    }
    
    public void setNguoiDung(NguoiDung nguoiDung) {
        this.nguoiDung = nguoiDung;
    }
    
    public List<ChiTietDonHang> getChiTietDonHangs() {
        return chiTietDonHangs;
    }
    
    public void setChiTietDonHangs(List<ChiTietDonHang> chiTietDonHangs) {
        this.chiTietDonHangs = chiTietDonHangs;
    }
}