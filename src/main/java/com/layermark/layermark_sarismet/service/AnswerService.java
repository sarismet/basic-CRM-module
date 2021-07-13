package com.layermark.layermark_sarismet.service;

import com.layermark.layermark_sarismet.exception.bad_request.BadRequestException;
import com.layermark.layermark_sarismet.exception.not_found.ResourceNotFoundException;
import com.layermark.layermark_sarismet.generic.CustomMessageSource;
import com.layermark.layermark_sarismet.model.Answer;
import com.layermark.layermark_sarismet.model.Survey;
import com.layermark.layermark_sarismet.repository.AnswerRepository;
import com.layermark.layermark_sarismet.repository.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class AnswerService {
    private SurveyRepository surveyRepository;
    private AnswerRepository answerRepository;
    private CustomMessageSource messageSource;

    private static final String SURVEY_IS_ALREADY_ANSWERED_BY_USER = "error.user-already-answered-this-topic";
    private static final String SURVEY_DOES_NOT_EXIST = "error.survey.id-miss-match";
    private static final String SURVEY_DOES_HAVE_THAT_OPTION = "error.survey.does-not-have-that-option";

    @Autowired
    public void setSurveyRepository(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    @Autowired
    public void setAnswerRepository(AnswerRepository answerRepository) { this.answerRepository = answerRepository; }

    @Autowired
    public void setMessageSource(CustomMessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public Answer answerSurvey(Answer answer) {
        Survey survey = surveyRepository.findById(answer.getSurveyID()).orElseThrow(() -> new ResourceNotFoundException(messageSource.getMessage(SURVEY_DOES_NOT_EXIST)));
        if ((answerRepository.findByUsernameAndSurveyID(answer.getUserName(),answer.getSurveyID())!=null)) {
            throw new BadRequestException(messageSource.getMessage(SURVEY_IS_ALREADY_ANSWERED_BY_USER));
        }

        if (!survey.getOptions().containsKey(answer.getOptionKey())) {
            throw new BadRequestException(messageSource.getMessage(SURVEY_DOES_HAVE_THAT_OPTION));
        }
        return answerRepository.insert(answer);
    }

    public HashMap<String, HashMap<String, Integer>>analyzeAnswers() {
        List<Answer> answers = answerRepository.findAll();
        HashMap<String, HashMap<String, Integer>> analysisReport = new HashMap<>();
        for (Answer answer : answers) {
            HashMap<String, Integer> surveyReport = analysisReport.get(answer.getSurveyID());
            if(surveyReport!= null){
                Integer answerCount = surveyReport.get(answer.getOptionKey());
                if (answerCount != null){
                    answerCount++;
                    surveyReport.put(answer.getOptionKey(),answerCount);
                }else{
                    surveyReport.put(answer.getOptionKey(),1);
                    analysisReport.put(answer.getSurveyID(),surveyReport);
                }
            }else{
                HashMap<String, Integer> newSurveyReport = new HashMap<>();
                newSurveyReport.put(answer.getOptionKey(),1);
                analysisReport.put(answer.getSurveyID(),newSurveyReport);
            }
        }
        return analysisReport;
    }
}
