package com.layermark.layermark_sarismet.repository;

import com.layermark.layermark_sarismet.model.NotVerifiedSurvey;
import com.layermark.layermark_sarismet.model.Survey;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotVerifiedSurveyRepository extends MongoRepository<NotVerifiedSurvey, String> {
    NotVerifiedSurvey findByTopic(String topic);
}