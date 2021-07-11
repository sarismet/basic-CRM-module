package com.layermark.layermark_sarismet.controller;

import com.layermark.layermark_sarismet.model.Answer;
import com.layermark.layermark_sarismet.model.Survey;
import com.layermark.layermark_sarismet.security.JWTUtility;
import com.layermark.layermark_sarismet.service.AnswerService;
import com.layermark.layermark_sarismet.service.SurveyService;
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
    @Autowired
    public void setJwtUtility(JWTUtility jwtUtility) {
        this.jwtUtility = jwtUtility;
    }

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    public void setAnswerService(AnswerService answerService) {
        this.answerService = answerService;
    }

    @Autowired
    public void setSurveyService(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @GetMapping("/id={name}")
    public Optional<Survey> getSurveyById(@PathVariable("name") String a) {
        return surveyService.getById(a);
    }

    @GetMapping
    public List<Survey> getAll() {
        return surveyService.getAllSurveys();
    }

    @PostMapping("/submit_opinion")
    public Answer submitOpinion(@RequestBody Answer answer){
        return answerService.answerSurvey(answer);
    }

    @GetMapping("/home")
    public String hello(){
        return "hello "+SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
