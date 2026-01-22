package edu.poly.ASM.service;

import edu.poly.ASM.dto.ReviewDTO;
import edu.poly.ASM.entity.Product;
import edu.poly.ASM.entity.Review;
import edu.poly.ASM.entity.User;
import edu.poly.ASM.repository.ProductRepository;
import edu.poly.ASM.repository.ReviewRepository;
import edu.poly.ASM.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.Optional;

@Service
@Transactional
public class ReviewService {
    
    @Autowired
    private ReviewRepository reviewRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    /**
     * Tạo đánh giá mới
     */
    public ReviewDTO createReview(ReviewDTO reviewDTO) {
        // Kiểm tra user đã đánh giá sản phẩm chưa
        if (reviewRepository.existsByUserIdAndProductId(reviewDTO.getUserId(), reviewDTO.getProductId())) {
            throw new RuntimeException("Bạn đã đánh giá sản phẩm này rồi");
        }
        
        User user = userRepository.findById(reviewDTO.getUserId())
            .orElseThrow(() -> new RuntimeException("Không tìm thấy user"));
        
        Product product = productRepository.findById(reviewDTO.getProductId())
            .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));
        
        Review review = new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setDiemDanhGia(reviewDTO.getDiemDanhGia());
        review.setNoiDung(reviewDTO.getNoiDung());
        review.setHinhAnh(reviewDTO.getHinhAnh());
        
        Review saved = reviewRepository.save(review);
        
        // Cập nhật điểm trung bình sản phẩm
        updateProductAverageRating(reviewDTO.getProductId());
        
        return convertToDTO(saved);
    }
    
    /**
     * Cập nhật điểm đánh giá trung bình của sản phẩm
     */
    private void updateProductAverageRating(Long productId) {
        Double avgRating = reviewRepository.calculateAverageRatingByProductId(productId);
        Product product = productRepository.findById(productId).orElse(null);
        if (product != null && avgRating != null) {
            product.setDanhGiaTrungBinh(BigDecimal.valueOf(avgRating));
            productRepository.save(product);
        }
    }
    
    /**
     * Lấy đánh giá theo sản phẩm
     */
    public Page<ReviewDTO> getReviewsByProductId(Long productId, Pageable pageable) {
        return reviewRepository.findByProductId(productId, pageable)
            .map(this::convertToDTO);
    }
    
    /**
     * Lấy đánh giá theo user
     */
    public Page<ReviewDTO> getReviewsByUserId(Long userId, Pageable pageable) {
        return reviewRepository.findByUserId(userId, pageable)
            .map(this::convertToDTO);
    }
    
    /**
     * Xóa đánh giá
     */
    public void deleteReview(Long id) {
        Review review = reviewRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy đánh giá"));
        
        Long productId = review.getProduct().getId();
        reviewRepository.deleteById(id);
        
        // Cập nhật lại điểm trung bình
        updateProductAverageRating(productId);
    }
    
    /**
     * Convert Review entity sang ReviewDTO
     */
    private ReviewDTO convertToDTO(Review review) {
        ReviewDTO dto = new ReviewDTO();
        dto.setId(review.getId());
        dto.setProductId(review.getProduct().getId());
        dto.setProductName(review.getProduct().getTenSanPham());
        dto.setUserId(review.getUser().getId());
        dto.setUserName(review.getUser().getTen());
        dto.setUserAvatar(review.getUser().getAvatar());
        dto.setDiemDanhGia(review.getDiemDanhGia());
        dto.setNoiDung(review.getNoiDung());
        dto.setHinhAnh(review.getHinhAnh());
        dto.setCreatedAt(review.getCreatedAt());
        return dto;
    }
}