package edu.poly.ASM.repository;

import edu.poly.ASM.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    
    
    // Tìm user theo số điện thoại (dùng cho login)
    Optional<User> findBySoDienThoai(String soDienThoai);
    
    // Tìm user theo email
    Optional<User> findByEmail(String email);
    
    // Kiểm tra số điện thoại đã tồn tại chưa
    boolean existsBySoDienThoai(String soDienThoai);
    
    // Kiểm tra email đã tồn tại chưa
    boolean existsByEmail(String email);
    
    // Lấy user theo role
    Page<User> findByRole(String role, Pageable pageable);
    
    // Lấy user đang hoạt động
    Page<User> findByTrangThai(Boolean trangThai, Pageable pageable);
    
    // Tìm kiếm user
    @Query("SELECT u FROM User u WHERE " +
           "u.ten LIKE %:keyword% OR " +
           "u.soDienThoai LIKE %:keyword% OR " +
           "u.email LIKE %:keyword%")
    Page<User> searchUsers(@Param("keyword") String keyword, Pageable pageable);
    
    // Đếm số lượng user theo role
    long countByRole(String role);
    
    // Đếm số lượng user đang hoạt động
    long countByTrangThai(Boolean trangThai);
    
    // Custom method for findByUsername (alias for findBySoDienThoai)
    default Optional<User> findByUsername(String username) {
        return findBySoDienThoai(username);
    }
}