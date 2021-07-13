package com.layermark.layermark_sarismet.controller;

import com.layermark.layermark_sarismet.model.Answer;
import com.layermark.layermark_sarismet.model.ChangePasswordRequest;
import com.layermark.layermark_sarismet.model.NotVerifiedSurvey;
import com.layermark.layermark_sarismet.model.Survey;
import com.layermark.layermark_sarismet.security.JWTUtility;
import com.layermark.layermark_sarismet.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/endUser")
public class EndUserController {

    private CustomFilterService customFilterService;
    private SurveyService surveyService;
    private AnswerService answerService;
    private EmailService emailService;
    private UserService userService;

    @Autowired
    public void setCustomFilterService(CustomFilterService customFilterService) {
        this.customFilterService = customFilterService;
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

    @GetMapping("/getAllSurveys")
    public List<Survey> getAll() {
        return surveyService.getAllSurveys();
    }

    @PostMapping("/submit_opinion")
    public Answer submitOpinion(HttpServletRequest request, @RequestBody Answer answer){
        customFilterService.checkUserNameMatch(request,answer.getUserName());
        return answerService.answerSurvey(answer);
    }

    @PostMapping("/change_password")
    public String changePassword(HttpServletRequest request,@RequestBody ChangePasswordRequest changePasswordRequest){
        customFilterService.checkUserNameMatch(request,changePasswordRequest.getUsername());
        return userService.changePassword(changePasswordRequest);
    }

    @GetMapping("/get_survey_results")
    public HashMap<String, HashMap<String, Integer>> getResults(){
        return answerService.analyzeAnswers();
    }

    @GetMapping("/home")
    public String hello(){
        return "hello "+SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
