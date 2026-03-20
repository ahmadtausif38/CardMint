package com.cardprocessor.handler;

public class CardProcessorException extends RuntimeException {

    private final ErrorType type;

    public CardProcessorException(ErrorType type, String message) {
        super(message);
        this.type = type;
    }

    public ErrorType getType() {
        return type;
    }

}
