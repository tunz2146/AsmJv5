// package edu.poly.ASM.service;

// import edu.poly.ASM.entity.User;
// import edu.poly.ASM.repository.UserRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.stereotype.Service;
// import java.util.Collections;
// import java.util.Set;

// @Service
// public class CustomUserDetailsService implements UserDetailsService {

//     @Autowired
//     private UserRepository userRepository;

//     @Override
//     public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//         User user = userRepository.findByUsername(username)
//                 .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

//         if (!user.getIsActive()) {
//             throw new UsernameNotFoundException("User is inactive: " + username);
//         }

//         // Tạo authority từ role
//         Set<GrantedAuthority> authorities = Collections.singleton(
//                 new SimpleGrantedAuthority("ROLE_" + user.getRole())
//         );

//         return org.springframework.security.core.userdetails.User.builder()
//                 .username(user.getUsername())
//                 .password(user.getPassword())
//                 .authorities(authorities)
//                 .accountExpired(false)
//                 .accountLocked(false)
//                 .credentialsExpired(false)
//                 .disabled(false)
//                 .build();
//     }
// }
