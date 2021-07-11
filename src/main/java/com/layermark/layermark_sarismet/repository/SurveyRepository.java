package com.layermark.layermark_sarismet.repository;


import com.layermark.layermark_sarismet.model.Survey;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyRepository extends MongoRepository<Survey, String> {
}