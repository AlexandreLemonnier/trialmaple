package com.trialmaple.core.exception;

public class InvalidMapException extends RuntimeException {
    private static final ErrorCode CODE = ErrorCode.INVALID_MAP;

    public InvalidMapException(String uuid) {
        super("Map not found: " + uuid);
    }

    public ErrorCode getCode() {
        return CODE;
    }
}
