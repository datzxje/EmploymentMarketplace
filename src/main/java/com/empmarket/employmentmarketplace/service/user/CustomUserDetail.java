package com.empmarket.employmentmarketplace.service.user;

import com.empmarket.employmentmarketplace.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component("userDetailsService")
@RequiredArgsConstructor
public class CustomUserDetail implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.empmarket.employmentmarketplace.entity.User user = userRepository.findByEmail(username);

        return new User(user.getEmail(), user.getPassword(),
                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
    }
}
