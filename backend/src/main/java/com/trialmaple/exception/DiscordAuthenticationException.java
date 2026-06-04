package com.trialmaple.exception;

import lombok.Getter;

@Getter
public class DiscordAuthenticationException extends RuntimeException {
    private final DiscordErrorCode code;

    public DiscordAuthenticationException(DiscordErrorCode code, String message) {
        super(message);
        this.code = code;
    }

}