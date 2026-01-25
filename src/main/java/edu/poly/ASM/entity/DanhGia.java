package edu.poly.ASM.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "Danh_Gia")
public class DanhGia {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_nguoi_dung", nullable = false)
    private NguoiDung nguoiDung;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_san_pham", nullable = false)
    private SanPham sanPham;
    
    @Column(name = "diem_danh_gia")
    private Integer diemDanhGia; // 1-5 stars
    
    @Column(name = "noi_dung", columnDefinition = "NVARCHAR(MAX)")
    private String noiDung;
    
    @Column(name = "hinh_anh", columnDefinition = "NVARCHAR(MAX)")
    private String hinhAnh; // JSON array
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Constructors
    public DanhGia() {}
    
    public DanhGia(NguoiDung nguoiDung, SanPham sanPham, Integer diemDanhGia, String noiDung) {
        this.nguoiDung = nguoiDung;
        this.sanPham = sanPham;
        this.diemDanhGia = diemDanhGia;
        this.noiDung = noiDung;
    }
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
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
    
    public SanPham getSanPham() {
        return sanPham;
    }
    
    public void setSanPham(SanPham sanPham) {
        this.sanPham = sanPham;
    }
    
    public Integer getDiemDanhGia() {
        return diemDanhGia;
    }
    
    public void setDiemDanhGia(Integer diemDanhGia) {
        this.diemDanhGia = diemDanhGia;
    }
    
    public String getNoiDung() {
        return noiDung;
    }
    
    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }
    
    public String getHinhAnh() {
        return hinhAnh;
    }
    
    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}