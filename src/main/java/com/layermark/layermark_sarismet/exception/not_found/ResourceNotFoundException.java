package com.layermark.layermark_sarismet.exception.not_found;

public class ResourceNotFoundException extends RuntimeException {
    private String message;

    public ResourceNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
