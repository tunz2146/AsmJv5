package edu.poly.ASM.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Loai_San_Pham")
public class LoaiSanPham {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "ten", nullable = false, length = 255)
    private String ten;
    
    @Column(name = "slug", length = 255)
    private String slug;
    
    @ManyToMany(mappedBy = "loaiSanPhams", fetch = FetchType.LAZY)
    private List<SanPham> sanPhams;
    
    public LoaiSanPham() {}
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getTen() { return ten; }
    public void setTen(String ten) { this.ten = ten; }
    
    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }
}