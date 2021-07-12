package com.layermark.layermark_sarismet.exception.bad_request;

public class UserIsAlreadyRegisteredException extends RuntimeException {
    private String message;
    public UserIsAlreadyRegisteredException(String message) {
        super(message);
        this.message = message;
    }
}