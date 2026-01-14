package com.trialmaple.exception;

public class InvalidMapNameException extends RuntimeException {
    private static final ErrorCode CODE = ErrorCode.INVALID_MAP_NAME;

    public InvalidMapNameException(String mapName) {
        super("Map name not found: " + mapName);
    }

    public ErrorCode getCode() {
        return CODE;
    }
}
