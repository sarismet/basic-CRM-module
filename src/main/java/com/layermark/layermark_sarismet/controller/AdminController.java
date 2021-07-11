package com.layermark.layermark_sarismet.controller;

import com.layermark.layermark_sarismet.model.Survey;
import com.layermark.layermark_sarismet.model.User;
import com.layermark.layermark_sarismet.security.JWTUtility;
import com.layermark.layermark_sarismet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.Map;

@RestController
@RequestMapping("/admin")

public class AdminController {

    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @PostMapping("/authenticate")
    public String authenticate() throws Exception{

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            "ismet",
                            "sari"
                    )
            );
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }

        final UserDetails userDetails
                = userService.loadUserByUsername("ismet");

        final String token =
                jwtUtility.generateToken(userDetails);

        return token;
    }

    @PostMapping("/create_survey")
    public String createSurvey(@RequestBody Map<String, String> options){
        return "createSurvey is done!";
    }

    @PutMapping("/update_survey/id={_id}")
    public String updateSurvey(@RequestBody Survey survey, @PathVariable("_id") String id){
        return "updateSurvey is done!";
    }

    @PostMapping("/create_users")
    public String createUsers(@RequestBody Map<String, String> options){
        return "createUsers is done!";
    }

    @DeleteMapping("/delete_survey/id={_id}")
    public String deleteSurvey(@PathVariable("_id") String id){
        return "deleteSurvey is done!";
    }

    @GetMapping("/home")
    public String hello(){
        return "hello from server side!";
    }
}


