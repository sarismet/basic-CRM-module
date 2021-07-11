package com.layermark.layermark_sarismet.exception.bad_request;

public class SurveyIsAnsweredException extends RuntimeException {
    private String message;
    public SurveyIsAnsweredException(String message) {
        super(message);
        this.message = message;
    }
}