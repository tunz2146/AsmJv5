package edu.poly.ASM.entity;

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
@Table(name = "Hang")
public class Hang {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "ten_hang", nullable = false, length = 255)
    private String tenHang;
    
    @OneToMany(mappedBy = "hang", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SanPham> sanPhams;
    
    // Constructors
    public Hang() {}
    
    public Hang(Long id, String tenHang) {
        this.id = id;
        this.tenHang = tenHang;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTenHang() {
        return tenHang;
    }
    
    public void setTenHang(String tenHang) {
        this.tenHang = tenHang;
    }
    
    public List<SanPham> getSanPhams() {
        return sanPhams;
    }
    
    public void setSanPhams(List<SanPham> sanPhams) {
        this.sanPhams = sanPhams;
    }
}