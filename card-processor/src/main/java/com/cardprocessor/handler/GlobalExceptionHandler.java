package com.cardprocessor.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CardProcessorException.class)
    public ResponseEntity<ErrorResponse> handleCardProcessorException(CardProcessorException ex) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(ex.getType())
                .message(ex.getMessage())
                .time(Instant.now())
                .build();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

}
