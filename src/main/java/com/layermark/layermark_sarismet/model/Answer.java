package com.layermark.layermark_sarismet.model;


import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@EqualsAndHashCode
@ToString
public class Answer {
    private String surveyID;
    private String username;
    private String optionKey;

    public Answer(String surveyID, String username, String optionKey) {
        this.surveyID = surveyID;
        this.username = username;
        this.optionKey = optionKey;
    }

    public void setSurveyID(String surveyID) {
        this.surveyID = surveyID;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public void setOptionKey(String optionKey) {
        this.optionKey = optionKey;
    }

    public String getSurveyID() {
        return surveyID;
    }

    public String getUserName() {
        return username;
    }

    public String getOptionKey() {
        return optionKey;
    }
}
