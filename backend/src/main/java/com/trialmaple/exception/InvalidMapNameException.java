package com.trialmaple.exception;

public class InvalidMapNameException extends RuntimeException {
    private final String code = "INVALID_MAP_NAME";

    public InvalidMapNameException(String mapName) {
        super("Map name not found: " + mapName);
    }

    public String getCode() {
        return code;
    }
}
