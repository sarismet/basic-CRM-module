package com.layermark.layermark_sarismet.service;


import ch.qos.logback.classic.spi.LoggingEventVO;
import com.layermark.layermark_sarismet.model.UserRole;
import com.layermark.layermark_sarismet.model.*;

import com.layermark.layermark_sarismet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import java.util.Arrays;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CustomUser user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        List<SimpleGrantedAuthority> roles = null;
        if (user.getRole().equals("ADMIN")) {
            roles = Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        else if (user.getRole().equals("BASIC")) {
            roles = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
        }
        return new User(user.getUsername(),user.getPassword(),roles);
    }

    public CustomUser save(CustomUserDTO user) {
        System.out.println("UserRole.BASIC.name() "+UserRole.BASIC.name());
        CustomUser newUser = new CustomUser();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setRole(UserRole.ADMIN.name());
        return userRepository.save(newUser);
    }
}
