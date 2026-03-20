package com.cardmint.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ErrorResponse> handleAppException(AppException ex) {

        ErrorResponse error = new ErrorResponse(
                ex.getErrorCode(),
                ex.getErrorType(),
                ex.getMessage(),
                LocalDateTime.now(),
                ex.getErrors() // optional, if you want field-level errors
        );

        // Use BAD_REQUEST if status is null
        HttpStatus status = ex.getStatus() != null ? ex.getStatus() : HttpStatus.BAD_REQUEST;

        return new ResponseEntity<>(error, status);
    }
}
