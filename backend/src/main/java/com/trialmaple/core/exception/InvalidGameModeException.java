package com.trialmaple.core.exception;

public class InvalidGameModeException extends RuntimeException {
    private static final ErrorCode CODE = ErrorCode.INVALID_GAME_MODE;

    public InvalidGameModeException(String gameMode) {
        super("Game mode not found: " + gameMode);
    }

    public ErrorCode getCode() {
        return CODE;
    }
}
