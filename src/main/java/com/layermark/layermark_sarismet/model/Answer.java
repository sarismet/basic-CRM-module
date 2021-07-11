package com.layermark.layermark_sarismet.model;


import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@EqualsAndHashCode
@ToString
public class Answer {
    private String surveyID;
    private String userName;
    private String optionKey;

    public Answer(String surveyID, String userName, String optionKey) {
        this.surveyID = surveyID;
        this.userName = userName;
        this.optionKey = optionKey;
    }

    public void setSurveyID(String surveyID) {
        this.surveyID = surveyID;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setOptionKey(String optionKey) {
        this.optionKey = optionKey;
    }

    public String getSurveyID() {
        return surveyID;
    }

    public String getUserName() {
        return userName;
    }

    public String getOptionKey() {
        return optionKey;
    }
}
