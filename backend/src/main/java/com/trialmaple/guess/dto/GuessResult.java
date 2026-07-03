package com.trialmaple.guess.dto;

public record GuessResult(
        boolean success,
        GuessDto guess
) {}
