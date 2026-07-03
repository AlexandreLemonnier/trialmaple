package com.trialmaple.core.exception;

public class InvalidAttemptException extends RuntimeException {
    private static final ErrorCode CODE = ErrorCode.INVALID_ATTEMPT;

    public InvalidAttemptException(String attempt) {
        super("Wrong attempt number: " + attempt);
    }

    public ErrorCode getCode() {
        return CODE;
    }
}
