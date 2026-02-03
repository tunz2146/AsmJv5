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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Nguoi_Dung")

public class NguoiDung {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "so_dien_thoai", unique = true, nullable = false, length = 20)
    private String soDienThoai;
    
    @Column(name = "ten", length = 255)
    private String ten;
    
    @Column(name = "password", nullable = false, length = 255)
    private String password;
    
    @Column(name = "avatar", length = 255)
    private String avatar;
    
    @Column(name = "gioi_tinh")
    private Boolean gioiTinh;
    
    @Column(name = "ngay_sinh")
    private LocalDate ngaySinh;
    
    @Column(name = "email", length = 255)
    private String email;
    
    @OneToMany(mappedBy = "nguoiDung", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DiaChi> diaChis;
    
    @OneToMany(mappedBy = "nguoiDung", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DonHang> donHangs;
    
    @OneToMany(mappedBy = "nguoiDung", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<GioHang> gioHangs;
    
    @OneToMany(mappedBy = "nguoiDung", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DanhGia> danhGias;
    
    // Constructors
    public NguoiDung() {}
    
    public NguoiDung(Long id, String soDienThoai, String ten, String password, String email) {
        this.id = id;
        this.soDienThoai = soDienThoai;
        this.ten = ten;
        this.password = password;
        this.email = email;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getSoDienThoai() {
        return soDienThoai;
    }
    
    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }
    
    public String getTen() {
        return ten;
    }
    
    public void setTen(String ten) {
        this.ten = ten;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getAvatar() {
        return avatar;
    }
    
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    
    public Boolean getGioiTinh() {
        return gioiTinh;
    }
    
    public void setGioiTinh(Boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }
    
    public LocalDate getNgaySinh() {
        return ngaySinh;
    }
    
    public void setNgaySinh(LocalDate ngaySinh) {
        this.ngaySinh = ngaySinh;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public List<DiaChi> getDiaChis() {
        return diaChis;
    }
    
    public void setDiaChis(List<DiaChi> diaChis) {
        this.diaChis = diaChis;
    }
    
    public List<DonHang> getDonHangs() {
        return donHangs;
    }
    
    public void setDonHangs(List<DonHang> donHangs) {
        this.donHangs = donHangs;
    }
    
    public List<GioHang> getGioHangs() {
        return gioHangs;
    }
    
    public void setGioHangs(List<GioHang> gioHangs) {
        this.gioHangs = gioHangs;
    }
    
    public List<DanhGia> getDanhGias() {
        return danhGias;
    }
    
    public void setDanhGias(List<DanhGia> danhGias) {
        this.danhGias = danhGias;
    }

    public String getRole() {
    throw new UnsupportedOperationException("Unimplemented method 'getRole'");
    }

}