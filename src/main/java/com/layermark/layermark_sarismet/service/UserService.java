package com.layermark.layermark_sarismet.service;


import ch.qos.logback.classic.spi.LoggingEventVO;
import com.layermark.layermark_sarismet.model.UserRole;
import com.layermark.layermark_sarismet.model.*;

import com.layermark.layermark_sarismet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

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
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return new User("admin","password",new ArrayList<>());
    }

    public CustomUser save(CustomUserDTO user) {
        System.out.println("UserRole.BASIC.name() "+UserRole.BASIC.name());
        CustomUser newUser = new CustomUser();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setRole(UserRole.BASIC.name());
        return userRepository.save(newUser);
    }
}
