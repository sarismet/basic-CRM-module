package com.layermark.layermark_sarismet.controller;


import com.layermark.layermark_sarismet.exception.bad_request.BadRequestException;
import com.layermark.layermark_sarismet.model.CustomUserDTO;
import com.layermark.layermark_sarismet.model.JwtRequest;
import com.layermark.layermark_sarismet.model.JwtResponse;
import com.layermark.layermark_sarismet.security.JWTUtility;
import com.layermark.layermark_sarismet.service.CryptoService;
import com.layermark.layermark_sarismet.service.EmailService;
import com.layermark.layermark_sarismet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private JWTUtility jwtUtility;
    private AuthenticationManager authenticationManager;
    private UserService userService;
    private EmailService emailService;
    private CryptoService cryptoService;

    @Autowired
    public void setJwtUtility(JWTUtility jwtUtility) {
        this.jwtUtility = jwtUtility;
    }

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    @Autowired
    public void setCryptoService(CryptoService cryptoService) {
        this.cryptoService = cryptoService;
    }

    @PostMapping("/auth_user")
    public ResponseEntity<?> authenticate(@RequestBody JwtRequest request){

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            throw new BadRequestException(e.getMessage());
        }

        final UserDetails userDetails
                = userService.loadUserByUsername(request.getUsername());

        final String token =
                jwtUtility.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerRequest(HttpServletRequest request, @RequestBody CustomUserDTO user) {
        return ResponseEntity.ok(userService.registerUser(request,user));
    }

    @GetMapping("/register_verification/**")
    public ResponseEntity<?> registerVerification(HttpServletRequest request){
        String url = request.getRequestURL().toString();
        String token = url.substring(url.indexOf("=") + 1);
        return ResponseEntity.ok(userService.verifyUser(cryptoService.decryptText(token)));
    }

    @GetMapping("/home")
    public String hello(HttpServletRequest request){
        return "hello from server side. Version 1.0.1!";
    }

}
