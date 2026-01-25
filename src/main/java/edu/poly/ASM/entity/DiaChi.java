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

@Entity
@Table(name = "Dia_Chi")
public class DiaChi {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_nguoi_dung", nullable = false)
    private NguoiDung nguoiDung;
    
    @Column(name = "cong_ty", length = 255)
    private String congTy;
    
    @Column(name = "dia_chi", length = 255)
    private String diaChi;
    
    @Column(name = "tinh_thanh", length = 255)
    private String tinhThanh;
    
    @Column(name = "quan_huyen", length = 255)
    private String quanHuyen;
    
    @Column(name = "phuong_xa", length = 255)
    private String phuongXa;
    
    @Column(name = "ten_nguoi_nhan", length = 255)
    private String tenNguoiNhan;
    
    @Column(name = "so_nguoi_nhan", length = 20)
    private String soNguoiNhan;
    
    // Constructors
    public DiaChi() {}
    
    public DiaChi(Long id, NguoiDung nguoiDung, String diaChi, String tenNguoiNhan, String soNguoiNhan) {
        this.id = id;
        this.nguoiDung = nguoiDung;
        this.diaChi = diaChi;
        this.tenNguoiNhan = tenNguoiNhan;
        this.soNguoiNhan = soNguoiNhan;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public NguoiDung getNguoiDung() {
        return nguoiDung;
    }
    
    public void setNguoiDung(NguoiDung nguoiDung) {
        this.nguoiDung = nguoiDung;
    }
    
    public String getCongTy() {
        return congTy;
    }
    
    public void setCongTy(String congTy) {
        this.congTy = congTy;
    }
    
    public String getDiaChi() {
        return diaChi;
    }
    
    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }
    
    public String getTinhThanh() {
        return tinhThanh;
    }
    
    public void setTinhThanh(String tinhThanh) {
        this.tinhThanh = tinhThanh;
    }
    
    public String getQuanHuyen() {
        return quanHuyen;
    }
    
    public void setQuanHuyen(String quanHuyen) {
        this.quanHuyen = quanHuyen;
    }
    
    public String getPhuongXa() {
        return phuongXa;
    }
    
    public void setPhuongXa(String phuongXa) {
        this.phuongXa = phuongXa;
    }
    
    public String getTenNguoiNhan() {
        return tenNguoiNhan;
    }
    
    public void setTenNguoiNhan(String tenNguoiNhan) {
        this.tenNguoiNhan = tenNguoiNhan;
    }
    
    public String getSoNguoiNhan() {
        return soNguoiNhan;
    }
    
    public void setSoNguoiNhan(String soNguoiNhan) {
        this.soNguoiNhan = soNguoiNhan;
    }
}