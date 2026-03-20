package com.cardmint.common.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppException extends RuntimeException {

    private final String errorCode;
    private final String errorType;
    private final HttpStatus status;
    private final List<FieldError> errors;

    // Constructor with message only
    public AppException(String errorCode, String message, String errorType, HttpStatus status) {
        super(message);
        this.errorCode = errorCode;
        this.errorType = errorType;
        this.status = status;
        this.errors = Collections.emptyList();
    }

    // Constructor with field-level errors
    public AppException(String errorCode, String message, String errorType, HttpStatus status, Map<String, String> errorMap) {
        super(message);
        this.errorCode = errorCode;
        this.errorType = errorType;
        this.status = status;

        if (errorMap != null) {
            this.errors = errorMap.entrySet().stream()
                    .map(e -> new FieldError(e.getKey(), e.getValue()))
                    .collect(Collectors.toList());
        } else {
            this.errors = Collections.emptyList();
        }
    }

    public String getErrorCode() { return errorCode; }
    public String getErrorType() { return errorType; }
    public HttpStatus getStatus() { return status; }
    public List<FieldError> getErrors() { return errors; }

    // Inner class for field-level errors
        public record FieldError(String field, String message) {
    }
}