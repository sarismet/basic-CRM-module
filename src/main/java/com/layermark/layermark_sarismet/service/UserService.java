package com.layermark.layermark_sarismet.service;


import ch.qos.logback.classic.spi.LoggingEventVO;
import com.layermark.layermark_sarismet.model.UserRole;
import com.layermark.layermark_sarismet.model.*;

import com.layermark.layermark_sarismet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CustomUser user = userRepository.findByUsername(username);
        if (user == null) {
            System.out.println("BOSSS  "+user.getUsername());
            throw new UsernameNotFoundException(username);
        }

        System.out.println("username"+user.getUsername());
        System.out.println("password"+user.getPassword());
        System.out.println("role"+user.getRole());

        List<SimpleGrantedAuthority> roles = null;
        if (user.getRole().equals("ADMIN")) {

            System.out.println("adminadminadminadmin");
            roles = Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        else if (user.getRole().equals("BASIC")) {
            System.out.println("BASICBASICBASICBASIC");
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
