package com.trialmaple.service.guess;

import com.trialmaple.model.dto.GuessDto;

public record GuessResult(
        boolean success,
        GuessDto guess
) {}
