package com.layermark.layermark_sarismet.controller;

import com.layermark.layermark_sarismet.model.CustomUserDTO;
import com.layermark.layermark_sarismet.model.UserRole;
import com.layermark.layermark_sarismet.security.JWTUtility;
import com.layermark.layermark_sarismet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/endUser")
public class EndUserController {

    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @GetMapping("/home")
    public String hello(Authentication authentication){
        boolean isUser = false;
        for (GrantedAuthority ga : authentication.getAuthorities()) {
            System.out.println("ga.getAuthority() "+ ga.getAuthority());
            if (ga.getAuthority().equals("ROLE_USER")){
                isUser = true;
                break;
            }
        }
        if (isUser) {
            return "you are a user";
        }
        return "you are not a user";
    }

    @PostMapping("/submit_opinion/survey_id={_survey_id}&options_id={_options_id}")
    public String submitOpinion(@PathVariable("_survey_id") String survey_id,@PathVariable("_options_id") String options_id){
        return "register is done!";
    }

    @GetMapping("/survey_id={_survey_id}&options_id={_options_id}")
    public String hello(@PathVariable("_survey_id") String survey_id,@PathVariable("_options_id") String options_id){
        return survey_id+options_id;
    }
}
