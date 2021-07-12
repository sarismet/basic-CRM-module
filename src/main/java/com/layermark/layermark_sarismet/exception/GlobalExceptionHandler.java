package com.layermark.layermark_sarismet.exception;

import com.layermark.layermark_sarismet.exception.bad_request.*;
import com.layermark.layermark_sarismet.exception.not_found.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.layermark.layermark_sarismet.model.Error;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ResourceNotFoundException.class)
    private ResponseEntity<Error> notFoundExceptionHandler( Exception e) {
        return exceptionHandler(HttpStatus.NOT_FOUND, e);
    }

    @ExceptionHandler({BadRequestException.class,UserIsAlreadyRegisteredException.class, UserEmailNotVerifiedException.class, InvalidCredentialException.class})
    private ResponseEntity<Error> badRequestExceptionHandler( Exception e) {
        return exceptionHandler(HttpStatus.BAD_REQUEST, e);
    }

    private ResponseEntity<Error> exceptionHandler(HttpStatus status, Exception e){
        Error error = new Error();
        error.setCode(status.toString());
        error.setError(e.getMessage().toString());
        return ResponseEntity.status(status).body(error);
    }
}
