package com.trialmaple.service.guess;

import org.springframework.stereotype.Service;

import com.trialmaple.exception.InvalidMapNameException;
import com.trialmaple.model.dto.GuessDto;
import com.trialmaple.model.dto.GuessRequestDto;
import com.trialmaple.model.entities.DailyMap;

@Service
public class GuessService {

    private final GuessServiceProvider provider;

    public GuessService(GuessServiceProvider provider) {
        this.provider = provider;
    }

    /**
     * Check if a guess is correct and give hints or correct elements
     */
    public GuessDto checkGuess(DailyMap dailyMap, GuessRequestDto request) throws InvalidMapNameException {
        IGuessGameModeService guessService = provider.getGuessService(dailyMap.getGameMode());
        return guessService.checkGuess(dailyMap, request);
    }
}
