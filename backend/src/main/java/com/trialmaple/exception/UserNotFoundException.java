package com.trialmaple.exception;

public class UserNotFoundException extends RuntimeException {
    private static final ErrorCode CODE = ErrorCode.INVALID_ATTEMPT;

    public UserNotFoundException(String userId) {
        super("User not found: " + userId);
    }

    public ErrorCode getCode() {
        return CODE;
    }
}