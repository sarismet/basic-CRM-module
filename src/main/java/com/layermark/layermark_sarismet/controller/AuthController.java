package com.layermark.layermark_sarismet.controller;


import com.layermark.layermark_sarismet.model.CustomUserDTO;
import com.layermark.layermark_sarismet.model.JwtRequest;
import com.layermark.layermark_sarismet.model.JwtResponse;
import com.layermark.layermark_sarismet.security.JWTUtility;
import com.layermark.layermark_sarismet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @PostMapping("/auth_user")
    public ResponseEntity<?> authenticate(@RequestBody JwtRequest request) throws Exception{

        System.out.println("111");

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        } catch (Exception e) {
            System.out.println("BadCredentialsException"+e);
            throw new Exception("INVALID_CREDENTIALS", e);
        }
        System.out.println("222");
        final UserDetails userDetails
                = userService.loadUserByUsername(request.getUsername());

        final String token =
                jwtUtility.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> saveUser(@RequestBody CustomUserDTO user) throws Exception {
        return ResponseEntity.ok(userService.save(user));
    }

    @GetMapping("/home")
    public String hello(){
        return "hello from server side!";
    }

}
