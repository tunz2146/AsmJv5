package edu.poly.ASM.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "Hang")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Brand {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "ten_hang", nullable = false)
    private String tenHang;
    
    @Column(name = "slug", unique = true)
    private String slug;
    
    @Column(name = "logo")
    private String logo;
    
    @Column(name = "mo_ta", columnDefinition = "NVARCHAR(MAX)")
    private String moTa;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (slug == null || slug.isEmpty()) {
            slug = createSlug(tenHang);
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
}