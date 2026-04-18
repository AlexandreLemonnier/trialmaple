package com.trialmaple.model.dto;

public record GuessRequestDto(
        String guessedMapUuid,
        String guessedMapName,
        int guessNumber,
        String dailyMapUuid
) {
}
