package com.trialmaple.exception;

public class InvalidGameModeException extends RuntimeException {
    private final ErrorCode code = ErrorCode.INVALID_GAME_MODE;

    public InvalidGameModeException(String gameMode) {
        super("Game mode not found: " + gameMode);
    }

    public ErrorCode getCode() {
        return code;
    }
}
