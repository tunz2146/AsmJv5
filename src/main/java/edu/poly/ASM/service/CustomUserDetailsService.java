package edu.poly.ASM.service;

import edu.poly.ASM.entity.NguoiDung;
import edu.poly.ASM.repository.NguoiDungRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final NguoiDungRepository nguoiDungRepository;

    public CustomUserDetailsService(NguoiDungRepository nguoiDungRepository) {
        this.nguoiDungRepository = nguoiDungRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String soDienThoai)
            throws UsernameNotFoundException {

        // Tìm người dùng theo số điện thoại
        NguoiDung nguoiDung = nguoiDungRepository.findBySoDienThoai(soDienThoai)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Không tìm thấy người dùng với số điện thoại: " + soDienThoai
                ));

        // Xác định quyền dựa vào số điện thoại (tạm thời)
        String role = "ROLE_USER";

        // ⭐ DANH SÁCH ADMIN (tạm thời cho ASM)
        // Có thể thêm nhiều số điện thoại admin vào đây
        if ("0987654321".equals(nguoiDung.getSoDienThoai()) ||
            "0794612606".equals(nguoiDung.getSoDienThoai())) {
            role = "ROLE_ADMIN";
        }

        // Trả về UserDetails với role phù hợp
        return User.builder()
                .username(nguoiDung.getSoDienThoai())
                .password(nguoiDung.getPassword())
                .authorities(Collections.singletonList(
                        new SimpleGrantedAuthority(role)
                ))
                .build();
    }
}