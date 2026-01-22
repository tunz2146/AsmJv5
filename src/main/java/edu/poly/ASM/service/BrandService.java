package edu.poly.ASM.service;

import edu.poly.ASM.dto.BrandDTO;
import edu.poly.ASM.entity.Brand;
import edu.poly.ASM.repository.BrandRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class BrandService {
    
    @Autowired
    private BrandRepository brandRepository;
    
    @Autowired
    private ModelMapper modelMapper;
    
    /**
     * Lấy tất cả hãng
     */
    public List<BrandDTO> getAllBrands() {
        return brandRepository.findAll().stream()
            .map(brand -> modelMapper.map(brand, BrandDTO.class))
            .collect(Collectors.toList());
    }
    
    /**
     * Lấy hãng theo ID
     */
    public Optional<BrandDTO> getBrandById(Long id) {
        return brandRepository.findById(id)
            .map(brand -> modelMapper.map(brand, BrandDTO.class));
    }
    
    /**
     * Tạo hãng mới
     */
    // public BrandDTO createBrand(BrandDTO brandDTO) {
    //     if (brandRepository.existsByTenHang(brandDTO.getTenHang())) {
    //         throw new RuntimeException("Tên hãng đã tồn tại");
    //     }
        
    //     Brand brand = modelMapper.map(brandDTO, Brand.class);
    //     Brand saved = brandRepository.save(brand);
    //     return modelMapper.map(saved, BrandDTO.class);
    // }
    
    /**
     * Cập nhật hãng
     */
    // public BrandDTO updateBrand(Long id, BrandDTO brandDTO) {
    //     Brand brand = brandRepository.findById(id)
    //         .orElseThrow(() -> new RuntimeException("Không tìm thấy hãng"));
        
    //     brand.setTenHang(brandDTO.getTenHang());
    //     brand.setLogo(brandDTO.getLogo());
    //     brand.setMoTa(brandDTO.getMoTa());
        
    //     Brand updated = brandRepository.save(brand);
    //     return modelMapper.map(updated, BrandDTO.class);
    // }
    
    /**
     * Xóa hãng
     */
    public void deleteBrand(Long id) {
        brandRepository.deleteById(id);
    }
    
    /**
     * Tìm kiếm hãng
     */
    public List<BrandDTO> searchBrands(String keyword) {
        return brandRepository.findByTenHangContainingIgnoreCase(keyword).stream()
            .map(brand -> modelMapper.map(brand, BrandDTO.class))
            .collect(Collectors.toList());
    }
}