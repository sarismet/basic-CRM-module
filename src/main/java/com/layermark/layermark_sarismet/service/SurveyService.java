package com.layermark.layermark_sarismet.service;

import com.layermark.layermark_sarismet.exception.bad_request.BadRequestException;
import com.layermark.layermark_sarismet.exception.not_found.ResourceNotFoundException;
import com.layermark.layermark_sarismet.generic.CustomMessageSource;
import com.layermark.layermark_sarismet.model.NotVerifiedSurvey;
import com.layermark.layermark_sarismet.model.Survey;
import com.layermark.layermark_sarismet.repository.NotVerifiedSurveyRepository;
import com.layermark.layermark_sarismet.repository.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SurveyService {
    private SurveyRepository surveyRepository;
    private NotVerifiedSurveyRepository notVerifiedSurveyRepository;
    private CustomMessageSource messageSource;

    private static final String SURVEY_IS_ANSWERED_UPDATE_NOT_ALLOWED = "error.survey.answered.update.not.allowed";
    private static final String SURVEY_DOES_NOT_EXIST = "error.survey.id-miss-match";
    private static final String SURVEY_WITH_THAT_TOPIC_EXISTS = "error.survey.topic-present";

    @Autowired
    public void setMessageSource(CustomMessageSource messageSource) {
        this.messageSource = messageSource;
    }
    @Autowired
    public void setSurveyRepository(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }
    @Autowired
    public void setNotVerifiedSurveyRepository(NotVerifiedSurveyRepository notVerifiedSurveyRepository) {
        this.notVerifiedSurveyRepository = notVerifiedSurveyRepository;
    }

    public Survey createSurvey(Survey survey) {
        if(surveyRepository.findByTopic(survey.getTopic())!=null){
            throw new BadRequestException(messageSource.getMessage(SURVEY_WITH_THAT_TOPIC_EXISTS));
        }
        return surveyRepository.insert(survey);
    }

    public NotVerifiedSurvey createNotVerifiedSurvey(NotVerifiedSurvey notVerifiedSurvey) {
        if(notVerifiedSurveyRepository.findByTopic(notVerifiedSurvey.getTopic())!=null){
            throw new BadRequestException(messageSource.getMessage(SURVEY_WITH_THAT_TOPIC_EXISTS));
        }
        return notVerifiedSurveyRepository.insert(notVerifiedSurvey);
    }

    public List<NotVerifiedSurvey> getNotVerifiedSurveys() {
        return notVerifiedSurveyRepository.findAll();
    }

    public NotVerifiedSurvey getNotVerifiedSurveyById(String id) {
        NotVerifiedSurvey NotVerifiedSurvey = notVerifiedSurveyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(messageSource.getMessage(SURVEY_DOES_NOT_EXIST)));
        return NotVerifiedSurvey;
    }

    public Survey verifyNotVerifiedSurvey(String id) {
        NotVerifiedSurvey notVerifiedSurvey = notVerifiedSurveyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(messageSource.getMessage(SURVEY_DOES_NOT_EXIST)));
        System.out.println("notVerifiedSurvey.getTopic()   "+notVerifiedSurvey.getTopic());
        if(surveyRepository.findByTopic(notVerifiedSurvey.getTopic())!=null){
            throw new BadRequestException(messageSource.getMessage(SURVEY_WITH_THAT_TOPIC_EXISTS));
        }
        Survey survey = surveyRepository.save(notVerifiedSurvey);
        notVerifiedSurveyRepository.deleteById(id);
        return survey;
    }

    public List<Survey> getAllSurveys() {
        return surveyRepository.findAll();
    }

    public Optional<Survey> getSurveyById(String id) {
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
