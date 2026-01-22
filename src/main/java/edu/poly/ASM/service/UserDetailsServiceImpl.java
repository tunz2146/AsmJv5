package edu.poly.ASM.service;

import edu.poly.ASM.entity.User;
import edu.poly.ASM.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        // 👉 username = số điện thoại
        User user = userRepository.findBySoDienThoai(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Không tìm thấy số điện thoại: " + username));

        // 👉 Check trạng thái tài khoản
        if (!Boolean.TRUE.equals(user.getIsActive())) {
            throw new DisabledException("Tài khoản đã bị khóa");
            }

         return org.springframework.security.core.userdetails.User
                .withUsername(user.getSoDienThoai())
                .password(user.getPassword())
                .roles(user.getRole()) // USER / ADMIN
                .disabled(!user.getIsActive())
                .build();
    }
}
