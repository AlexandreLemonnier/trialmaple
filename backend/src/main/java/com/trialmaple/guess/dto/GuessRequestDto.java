package com.trialmaple.guess.dto;

public record GuessRequestDto(
        String guessedMapUuid,
        String guessedMapName,
        int guessNumber,
        String dailyMapUuid
) {
}
