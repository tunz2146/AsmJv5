package edu.poly.ASM.service;

import edu.poly.ASM.entity.Category;
import edu.poly.ASM.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    /**
     * Lấy tất cả danh mục
     */
    public List<Category> getAllCategories() {
        return categoryRepository.findByIsActive(true);
    }
    
    /**
     * Lấy danh mục theo ID
     */
    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }
    
    /**
     * Tạo danh mục mới
     */
    public Category createCategory(Category category) {
        if (category.getIsActive() == null) {
            category.setIsActive(true);
        }
        return categoryRepository.save(category);
    }
    
    /**
     * Cập nhật danh mục
     */
    public Category updateCategory(Long id, Category categoryDetails) {
        Optional<Category> existing = categoryRepository.findById(id);
        if (existing.isPresent()) {
            Category category = existing.get();
            category.setName(categoryDetails.getName());
            category.setDescription(categoryDetails.getDescription());
            category.setIsActive(categoryDetails.getIsActive());
            return categoryRepository.save(category);
        }
        return null;
    }
    
    /**
     * Xóa danh mục (soft delete)
     */
    public void deleteCategory(Long id) {
        Optional<Category> existing = categoryRepository.findById(id);
        if (existing.isPresent()) {
            Category category = existing.get();
            category.setIsActive(false);
            categoryRepository.save(category);
        }
    }
}
