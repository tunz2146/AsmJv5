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

        // 1️⃣ Tìm người dùng theo số điện thoại
        NguoiDung nguoiDung = nguoiDungRepository.findBySoDienThoai(soDienThoai)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Không tìm thấy người dùng với số điện thoại: " + soDienThoai
                ));

        // 2️⃣ Xác định quyền (ROLE)
        String role = "ROLE_USER";

        // ADMIN cứng (demo / ASM)
        if ("0987654321".equals(nguoiDung.getSoDienThoai())) {
            role = "ROLE_ADMIN";
        }

        // 3️⃣ Trả về UserDetails
        return User.builder()
                .username(nguoiDung.getSoDienThoai())
                .password(nguoiDung.getPassword()) // 🔥 PASSWORD THƯỜNG
                .authorities(
                        Collections.singletonList(
                                new SimpleGrantedAuthority(role)
                        )
                )
                .build();
    }
}
