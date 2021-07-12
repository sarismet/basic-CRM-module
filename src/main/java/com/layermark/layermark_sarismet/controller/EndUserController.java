package com.layermark.layermark_sarismet.controller;

import com.layermark.layermark_sarismet.model.Answer;
import com.layermark.layermark_sarismet.model.ChangePasswordRequest;
import com.layermark.layermark_sarismet.model.NotVerifiedSurvey;
import com.layermark.layermark_sarismet.model.Survey;
import com.layermark.layermark_sarismet.security.JWTUtility;
import com.layermark.layermark_sarismet.service.AnswerService;
import com.layermark.layermark_sarismet.service.EmailService;
import com.layermark.layermark_sarismet.service.SurveyService;
import com.layermark.layermark_sarismet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/endUser")
public class EndUserController {

    private JWTUtility jwtUtility;
    private AuthenticationManager authenticationManager;
    private SurveyService surveyService;
    private AnswerService answerService;
    private EmailService emailService;
    private UserService userService;

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
    public void setAnswerService(AnswerService answerService) {
        this.answerService = answerService;
    }

    @Autowired
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    @Autowired
    public void setSurveyService(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @GetMapping("/id={_id}")
    public Optional<Survey> getSurveyById(@PathVariable("_id") String surveyID) {
        return surveyService.getSurveyById(surveyID);
    }

    @PostMapping("/create_not_verified_survey")
    public NotVerifiedSurvey createNotVerifiedSurvey(@RequestBody NotVerifiedSurvey notVerifiedSurvey){
        return surveyService.createNotVerifiedSurvey(notVerifiedSurvey);
    }

    @GetMapping
    public List<Survey> getAll() {
        return surveyService.getAllSurveys();
    }

    @PostMapping("/submit_opinion")
    public Answer submitOpinion(@RequestBody Answer answer){
        return answerService.answerSurvey(answer);
    }

    @PostMapping("/change_password")
    public String changePassword(@RequestBody ChangePasswordRequest changePasswordRequest){
        return userService.changePassword(changePasswordRequest);
    }

    @GetMapping("/home")
    public String hello(){
        return "hello "+SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
