package com.layermark.layermark_sarismet.repository;

import com.layermark.layermark_sarismet.model.Answer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends MongoRepository<Answer, String> {
    Answer findByUsernameAndSurveyID(String username,String surveyID);
}