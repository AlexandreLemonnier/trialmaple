package com.trialmaple.exception;

public class InvalidMapNameException extends RuntimeException {
    private final ErrorCode code = ErrorCode.INVALID_MAP_NAME;

    public InvalidMapNameException(String mapName) {
        super("Map name not found: " + mapName);
    }

    public ErrorCode getCode() {
        return code;
    }
}
