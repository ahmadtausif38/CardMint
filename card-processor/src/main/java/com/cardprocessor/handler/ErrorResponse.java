package com.cardprocessor.handler;


import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class ErrorResponse {

    private ErrorType errorCode;
    private String message;
    private Instant time;

}
