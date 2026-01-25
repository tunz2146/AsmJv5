package edu.poly.ASM.config;

import edu.poly.ASM.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                // 🔓 PUBLIC - Không cần đăng nhập
                .requestMatchers(
                    "/",
                    "/login",
                    "/register",
                    "/products/**",
                    "/css/**",
                    "/js/**",
                    "/images/**",
                    "/webjars/**",
                    "/error"
                ).permitAll()
                
                // 🔐 ADMIN ONLY - Chỉ admin mới vào được
                .requestMatchers("/admin/**").hasRole("ADMIN")
                
                // 🔒 USER - Cần đăng nhập
                .requestMatchers("/cart/**", "/orders/**", "/profile/**").hasAnyRole("USER", "ADMIN")
                
                // Tất cả request khác cần authenticated
                .anyRequest().authenticated()
            )

            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .successHandler((request, response, authentication) -> {
                    // Kiểm tra role và redirect
                    boolean isAdmin = authentication.getAuthorities().stream()
                        .anyMatch(grantedAuthority -> 
                            grantedAuthority.getAuthority().equals("ROLE_ADMIN"));
                    
                    if (isAdmin) {
                        response.sendRedirect("/admin/dashboard");
                    } else {
                        response.sendRedirect("/");
                    }
                })
                .failureUrl("/login?error=true")
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll()
            )

            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/?logout=true")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )

            .exceptionHandling(ex -> ex
                .accessDeniedPage("/403")
            )

            .csrf(csrf -> csrf.disable())
            .userDetailsService(userDetailsService);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}