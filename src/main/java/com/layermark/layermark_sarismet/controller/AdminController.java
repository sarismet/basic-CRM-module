package com.layermark.layermark_sarismet.controller;

import com.layermark.layermark_sarismet.model.Answer;
import com.layermark.layermark_sarismet.model.CustomUserDTO;
import com.layermark.layermark_sarismet.model.NotVerifiedSurvey;
import com.layermark.layermark_sarismet.model.Survey;
import com.layermark.layermark_sarismet.security.JWTUtility;
import com.layermark.layermark_sarismet.service.AnswerService;
import com.layermark.layermark_sarismet.service.SurveyService;
import com.layermark.layermark_sarismet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.List;

@RestController
@RequestMapping("/api/admin")

public class AdminController {

    private JWTUtility jwtUtility;
    private AuthenticationManager authenticationManager;
    private SurveyService surveyService;
    private UserService userService;
    private AnswerService answerService;

    @Autowired
    public void setJwtUtility(JWTUtility jwtUtility) {
        this.jwtUtility = jwtUtility;
    }

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    public void setSurveyService(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @Autowired
    public void setAnswerService(AnswerService answerService) {
        this.answerService = answerService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/newSurvey")
    public Survey createSurvey(@RequestBody Survey a) {
        return surveyService.createSurvey(a);
    }

    @PostMapping("/create_users")
    public ResponseEntity<?>  createUsers(@RequestBody CustomUserDTO user){
        return ResponseEntity.ok(userService.save(user));
    }

    @GetMapping("/get_all_not_verified_surveys")
    public List<NotVerifiedSurvey> getAllNotVerifiedSurveys(){
        return  surveyService.getNotVerifiedSurveys();
    }

    @PutMapping("/verify_not_verified_survey/id={_id}")
    public Survey verifyNotVerifiedSurvey(@PathVariable("_id") String id){
        return surveyService.verifyNotVerifiedSurvey(id);
    }

    @PutMapping("/update_survey/id={_id}")
    public Survey updateSurveyById(@RequestBody Survey survey,@PathVariable("_id") String id) {
        return surveyService.updateSurvey(survey,id);
    }

    @DeleteMapping("/delete_survey/id={_id}")
    public String deleteSurveyById(@PathVariable("_id") String id){
        surveyService.deleteSurvey(id);
        return String.format("Survey with id %s is deleted",id);
    }

    @GetMapping("/get_survey_results")
    public List<Answer> getResults(){
        return answerService.analyzeAnswers();
    }

    @GetMapping("/home")
    public String hello(){
        return "hello from server side!";
    }
}


