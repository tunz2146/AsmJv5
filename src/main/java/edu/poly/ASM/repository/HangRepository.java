package edu.poly.ASM.repository;

import edu.poly.ASM.entity.Hang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HangRepository extends JpaRepository<Hang, Long> {
    
    // Tìm hãng theo tên
    Optional<Hang> findByTenHang(String tenHang);
}