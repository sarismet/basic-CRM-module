package com.layermark.layermark_sarismet.service;

import com.layermark.layermark_sarismet.exception.not_found.ResourceNotFoundException;
import com.layermark.layermark_sarismet.generic.CustomMessageSource;
import com.layermark.layermark_sarismet.model.Answer;
import com.layermark.layermark_sarismet.repository.AnswerRepository;
import com.layermark.layermark_sarismet.repository.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class AnswerService {
    private SurveyRepository surveyRepository;
    private AnswerRepository answerRepository;
    private UserService userService;
    private CustomMessageSource messageSource;

    private static final String SURVEY_IS_ALREADY_ANSWERED = "error.survey.is-answered";
    private static final String SURVEY_DOES_NOT_EXIST = "error.survey.id-miss-match";

    @Autowired
    public void setSurveyRepository(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    @Autowired
    public void setAnswerRepository(AnswerRepository answerRepository) { this.answerRepository = answerRepository; }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public Answer answerSurvey(Answer answer) {
        surveyRepository.findById(answer.getSurveyID()).orElseThrow(() -> new ResourceNotFoundException(messageSource.getMessage(SURVEY_DOES_NOT_EXIST)));
        //TODO check if survey is already answered with that user.
        return answerRepository.insert(answer);
    }

    public List<Answer> analyzeAnswers() {
        List<Answer> answers = answerRepository.findAll();
        HashMap<String, HashMap<String, Integer>> analysisReport = new HashMap<>();
        for (Answer answer : answers) {
            HashMap<String, Integer> surveyReport = analysisReport.get(answer.getSurveyID());
            if(surveyReport!= null){
                Integer answerCount = surveyReport.get(answer.getOptionKey());
                if (answerCount != null){
                    answerCount++;
                    surveyReport.put(answer.getOptionKey(),answerCount);
                }
            }else{
                HashMap<String, Integer> newSurveyReport = new HashMap<>();
                newSurveyReport.put(answer.getOptionKey(),1);
                analysisReport.put(answer.getSurveyID(),newSurveyReport);
            }
        }
        return answers;
    }
}
