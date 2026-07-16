package com.trialmaple.core.exception;

public class UserNotFoundException extends RuntimeException {
    private static final ErrorCode CODE = ErrorCode.USER_NOT_FOUND;

    public UserNotFoundException(String userId) {
        super("User not found: " + userId);
    }

    public ErrorCode getCode() {
        return CODE;
    }
}