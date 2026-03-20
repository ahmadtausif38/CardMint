package com.cardmint.common.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
        String errorCode,
        String errorType,
        String message,
        LocalDateTime timestamp,
        List<AppException.FieldError> errors) {}
