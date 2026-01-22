package edu.poly.ASM.repository;

import edu.poly.ASM.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    
    // Lấy tất cả địa chỉ của user
    List<Address> findByUserId(Long userId);
    
    // Lấy địa chỉ mặc định của user
    Optional<Address> findByUserIdAndMacDinhTrue(Long userId);
    
    // Xóa trạng thái mặc định của tất cả địa chỉ của user
    @Modifying
    @Query("UPDATE Address a SET a.macDinh = false WHERE a.user.id = :userId")
    void clearDefaultAddressForUser(@Param("userId") Long userId);
    
    // Đếm số lượng địa chỉ của user
    long countByUserId(Long userId);
}