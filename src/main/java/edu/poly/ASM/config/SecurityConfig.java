package edu.poly.ASM.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authz -> authz
                // Cho phép truy cập công khai (xem sản phẩm, trang chủ, tài nguyên tĩnh)
                .requestMatchers("/").permitAll()
                .requestMatchers("/san-pham").permitAll()           // Danh sách sản phẩm
                .requestMatchers("/san-pham/tim-kiem").permitAll()  // Tìm kiếm
                .requestMatchers("/san-pham/{id}").permitAll()      // Chi tiết sản phẩm
                .requestMatchers("/css/**", "/js/**", "/images/**", "/uploads/**").permitAll()
                
                // Cho phép đăng ký/đăng nhập
                .requestMatchers("/auth/**").permitAll()
                
                // Yêu cầu đăng nhập để sử dụng các chức năng
                .requestMatchers("/gio-hang/**").authenticated()    // Giỏ hàng
                .requestMatchers("/don-hang/**").authenticated()    // Đơn hàng
                .requestMatchers("/profile/**").authenticated()     // Tài khoản
                
                // Admin panel
                .requestMatchers("/admin/**").hasRole("ADMIN")
                
                // Các request còn lại cần xác thực
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/auth/login")
                .loginProcessingUrl("/auth/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/", true)
                .failureUrl("/auth/login?error")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/auth/logout")
                .logoutSuccessUrl("/")
                .permitAll()
            );
        
        return http.build();
    }
}
