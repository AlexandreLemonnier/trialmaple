package com.trialmaple.model.dto;

public record GuessRequestDto(
        String guessedMapUuid,
        int guessNumber,
        String dailyMapUuid
) {
}
