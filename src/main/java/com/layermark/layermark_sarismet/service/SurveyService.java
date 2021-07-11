package com.layermark.layermark_sarismet.service;

import com.layermark.layermark_sarismet.exception.not_found.ResourceNotFoundException;
import com.layermark.layermark_sarismet.generic.CustomMessageSource;
import com.layermark.layermark_sarismet.model.Survey;
import com.layermark.layermark_sarismet.repository.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SurveyService {
    private SurveyRepository surveyRepository;
    private UserService userService;
    private CustomMessageSource messageSource;

    private static final String SURVEY_IS_ANSWERED_UPDATE_NOT_ALLOWED = "error.survey.answered.update.not.allowed";
    private static final String SURVEY_DOES_NOT_EXIST = "error.survey.id-miss-match";

    @Autowired
    public void setSurveyRepository(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public Survey createSurvey(Survey survey) {
        return surveyRepository.insert(survey);
    }

    public List<Survey> getAllSurveys() {
        return surveyRepository.findAll();
    }

    public Optional<Survey> getById(String id) {
        return surveyRepository.findById(id);
    }

    public Survey updateSurvey(Survey survey, String id) {
        Survey currentSurvey = surveyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(messageSource.getMessage(SURVEY_DOES_NOT_EXIST)));
        if (currentSurvey.isAnswered()){
            throw new ResourceNotFoundException(messageSource.getMessage(SURVEY_IS_ANSWERED_UPDATE_NOT_ALLOWED));
        }
        currentSurvey.setTopic(survey.getTopic());
        currentSurvey.setOptions(survey.getOptions());
        return surveyRepository.save(currentSurvey);
    }

    public void deleteSurvey(String id){
        surveyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(messageSource.getMessage(SURVEY_DOES_NOT_EXIST)));
        surveyRepository.deleteById(id);
    }

}
