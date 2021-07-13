package com.layermark.layermark_sarismet.controller;

import com.layermark.layermark_sarismet.model.CustomUserDTO;
import com.layermark.layermark_sarismet.model.NotVerifiedSurvey;
import com.layermark.layermark_sarismet.model.Survey;
import com.layermark.layermark_sarismet.model.VerifySurveyRequest;
import com.layermark.layermark_sarismet.service.AnswerService;
import com.layermark.layermark_sarismet.service.SurveyService;
import com.layermark.layermark_sarismet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/admin")

public class AdminController {

    private SurveyService surveyService;
    private UserService userService;
    private AnswerService answerService;

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

    @PostMapping("/create_user")
    public ResponseEntity<?>  createUser(@RequestBody CustomUserDTO user){
        return ResponseEntity.ok(userService.save(user,true));
    }

    @GetMapping("/get_all_not_verified_surveys")
    public List<NotVerifiedSurvey> getAllNotVerifiedSurveys(){
        return  surveyService.getNotVerifiedSurveys();
    }

    @GetMapping("/get_all_not_verified_surveys_by_id/id={_id}")
    public NotVerifiedSurvey getAllNotVerifiedSurvey(@PathVariable("_id") String id){
        return surveyService.getNotVerifiedSurveyById(id);
    }

    @PutMapping("/verify_not_verified_survey")
    public Survey verifyNotVerifiedSurvey(@RequestBody VerifySurveyRequest verifySurveyRequest){
        return surveyService.verifyNotVerifiedSurvey(verifySurveyRequest.getId());
    }

    @PutMapping("/update_survey/id={_id}")
    public Survey updateSurveyById(@RequestBody Survey survey,@PathVariable("_id") String id) {
        return surveyService.updateSurvey(survey,id);
    }

    @DeleteMapping("/delete_survey/id={_id}")
    public String deleteSurveyById(@PathVariable("_id") String id){
        surveyService.deleteSurvey(id);
        // We do not check here if the the survey is actually deleted or not.
        // We could connect to database and run findbyid command one more time to see or change our repository template.
        return String.format("Survey with id %s is deleted",id);
    }

    @GetMapping("/get_survey_results")
    public HashMap<String, HashMap<String, Integer>> getResults(){
        return answerService.analyzeAnswers();
    }

    @GetMapping("/home")
    public String hello(){
        return "hello from server side!";
    }
}


