package edu.poly.ASM.service;

import edu.poly.ASM.dto.UserDTO;
import edu.poly.ASM.entity.User;
import edu.poly.ASM.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    /**
     * Lấy tất cả user (có phân trang)
     */
    public Page<UserDTO> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
            .map(user -> modelMapper.map(user, UserDTO.class));
    }
    
    /**
     * Lấy user theo ID
     */
    public Optional<UserDTO> getUserById(Long id) {
        return userRepository.findById(id)
            .map(user -> modelMapper.map(user, UserDTO.class));
    }
    
    /**
     * Lấy user theo số điện thoại
     */
    public Optional<UserDTO> getUserBySoDienThoai(String soDienThoai) {
        return userRepository.findBySoDienThoai(soDienThoai)
            .map(user -> modelMapper.map(user, UserDTO.class));
    }
    
    /**
     * Lấy user theo email
     */
    public Optional<UserDTO> getUserByEmail(String email) {
        return userRepository.findByEmail(email)
            .map(user -> modelMapper.map(user, UserDTO.class));
    }
    
    /**
     * Đăng ký user mới
     */
    public UserDTO registerUser(UserDTO userDTO) {
        // Kiểm tra số điện thoại đã tồn tại
        if (userRepository.existsBySoDienThoai(userDTO.getSoDienThoai())) {
            throw new RuntimeException("Số điện thoại đã được sử dụng");
        }
        
        // Kiểm tra email đã tồn tại
        if (userDTO.getEmail() != null && userRepository.existsByEmail(userDTO.getEmail())) {
            throw new RuntimeException("Email đã được sử dụng");
        }
        
        User user = modelMapper.map(userDTO, User.class);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole("USER");
        user.setTrangThai(true);
        
        User saved = userRepository.save(user);
        return modelMapper.map(saved, UserDTO.class);
    }
    
    /**
     * Cập nhật thông tin user
     */
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy user"));
        
        user.setTen(userDTO.getTen());
        user.setEmail(userDTO.getEmail());
        user.setGioiTinh(userDTO.getGioiTinh());
        user.setNgaySinh(userDTO.getNgaySinh());
        user.setAvatar(userDTO.getAvatar());
        
        User updated = userRepository.save(user);
        return modelMapper.map(updated, UserDTO.class);
    }
    
    /**
     * Đổi mật khẩu
     */
    public void changePassword(Long id, String oldPassword, String newPassword) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy user"));
        
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new RuntimeException("Mật khẩu cũ không đúng");
        }
        
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
    
    /**
     * Khóa/Mở khóa user
     */
    public void toggleUserStatus(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy user"));
        
        user.setTrangThai(!user.getTrangThai());
        userRepository.save(user);
    }
    
    /**
     * Tìm kiếm user
     */
    public Page<UserDTO> searchUsers(String keyword, Pageable pageable) {
        return userRepository.searchUsers(keyword, pageable)
            .map(user -> modelMapper.map(user, UserDTO.class));
    }
    
    /**
     * Lấy user theo role
     */
    public Page<UserDTO> getUsersByRole(String role, Pageable pageable) {
        return userRepository.findByRole(role, pageable)
            .map(user -> modelMapper.map(user, UserDTO.class));
    }
    
    /**
     * Đếm số lượng user
     */
    public long countUsers() {
        return userRepository.count();
    }
    
    /**
     * Đếm số lượng user theo role
     */
    public long countUsersByRole(String role) {
        return userRepository.countByRole(role);
    }
}


