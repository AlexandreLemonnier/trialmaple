package com.trialmaple.core.exception;

import java.time.Instant;

public record ErrorResponseDto(
        Instant timestamp,
        String error,
        String message) {
}
