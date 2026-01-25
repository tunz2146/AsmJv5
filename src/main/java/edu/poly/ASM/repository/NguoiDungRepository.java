package edu.poly.ASM.repository;

import edu.poly.ASM.entity.NguoiDung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NguoiDungRepository extends JpaRepository<NguoiDung, Long> {
    
    // Tìm người dùng theo số điện thoại
    Optional<NguoiDung> findBySoDienThoai(String soDienThoai);
    
    // Tìm người dùng theo email
    Optional<NguoiDung> findByEmail(String email);
    
    // Kiểm tra số điện thoại đã tồn tại chưa
    Boolean existsBySoDienThoai(String soDienThoai);
    
    // Kiểm tra email đã tồn tại chưa
    Boolean existsByEmail(String email);
}