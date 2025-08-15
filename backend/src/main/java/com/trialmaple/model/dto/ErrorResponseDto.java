package com.trialmaple.model.dto;

import java.time.Instant;

public class ErrorResponseDto {
    private Instant timestamp;
    private String error;
    private String message;

    public ErrorResponseDto(Instant timestamp, String error, String message) {
        this.timestamp = timestamp;
        this.error = error;
        this.message = message;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}
