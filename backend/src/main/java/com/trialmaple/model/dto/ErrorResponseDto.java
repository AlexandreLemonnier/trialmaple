package com.trialmaple.model.dto;

import java.time.Instant;

public record ErrorResponseDto(
        Instant timestamp,
        String error,
        String message) {
}
