package com.trialmaple.model.dto;

public record GuessRequestDto(
        String guess,
        int guessNumber,
        String dailyMapUuid
) {
}
