package com.layermark.layermark_sarismet.exception.bad_request;

public class InvalidCredentialException extends RuntimeException {
    private String message;
    public InvalidCredentialException(String message) {
        super(message);
        this.message = message;
    }
}