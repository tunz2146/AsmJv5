package edu.poly.ASM.repository;

import edu.poly.ASM.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    
    // Lấy đánh giá theo sản phẩm (có phân trang)
    Page<Review> findByProductId(Long productId, Pageable pageable);
    
    // Lấy đánh giá theo user
    Page<Review> findByUserId(Long userId, Pageable pageable);
    
    // Kiểm tra user đã đánh giá sản phẩm chưa
    boolean existsByUserIdAndProductId(Long userId, Long productId);
    
    // Lấy đánh giá cụ thể của user cho sản phẩm
    Optional<Review> findByUserIdAndProductId(Long userId, Long productId);
    
    // Đếm số lượng đánh giá của sản phẩm
    long countByProductId(Long productId);
    
    // Tính điểm trung bình của sản phẩm
    @Query("SELECT AVG(r.diemDanhGia) FROM Review r WHERE r.product.id = :productId")
    Double calculateAverageRatingByProductId(@Param("productId") Long productId);
    
    // Lấy đánh giá theo số sao
    Page<Review> findByProductIdAndDiemDanhGia(Long productId, Integer diemDanhGia, Pageable pageable);
    
    // Đếm số lượng đánh giá theo từng mức sao
    @Query("SELECT r.diemDanhGia, COUNT(r) FROM Review r " +
           "WHERE r.product.id = :productId GROUP BY r.diemDanhGia")
    List<Object[]> countReviewsByRating(@Param("productId") Long productId);
}