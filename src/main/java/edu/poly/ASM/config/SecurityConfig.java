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
            // PHÂN QUYỀN
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/",
                    "/login",
                    "/register",
                    "/products/**",
                    "/css/**",
                    "/js/**",
                    "/images/**",
                    "/webjars/**"
                ).permitAll()
                .anyRequest().permitAll() // tạm thời cho hết
            )

            // FORM LOGIN
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?error=true")
                .usernameParameter("username") // số điện thoại
                .passwordParameter("password")
                .permitAll()
            )

            // LOGOUT
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/?logout=true")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )

            // TẮT CSRF (CHO DỄ TEST)
            .csrf(csrf -> csrf.disable())

            // USER DETAILS SERVICE
            .userDetailsService(userDetailsService);

        return http.build();
    }

    // 🔥 PASSWORD THƯỜNG – KHÔNG MÃ HÓA
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
