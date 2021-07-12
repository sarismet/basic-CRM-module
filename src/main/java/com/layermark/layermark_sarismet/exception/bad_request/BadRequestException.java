package com.layermark.layermark_sarismet.exception.bad_request;

public class BadRequestException extends RuntimeException {
    private String message;
    public BadRequestException(String message) {
        super(message);
        this.message = message;
    }
}