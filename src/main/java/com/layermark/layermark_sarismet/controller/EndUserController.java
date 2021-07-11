package com.layermark.layermark_sarismet.controller;

import com.layermark.layermark_sarismet.model.CustomUserDTO;
import com.layermark.layermark_sarismet.security.JWTUtility;
import com.layermark.layermark_sarismet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/endUser")
public class EndUserController {

    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @GetMapping("/home")
    public String hello(){
        return "hello from server side!";
    }

    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> options){
        return "login is done!";
    }

    @PostMapping("/register")
    public ResponseEntity<?> saveUser(@RequestBody CustomUserDTO user) throws Exception {
        return ResponseEntity.ok(userService.save(user));
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
