package com.layermark.layermark_sarismet.exception.bad_request;

public class UserEmailNotVerifiedException extends RuntimeException {
    private String message;
    public UserEmailNotVerifiedException(String message) {
        super(message);
        this.message = message;
    }
}