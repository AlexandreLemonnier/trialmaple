package com.trialmaple.service.guess;

import com.trialmaple.exception.InvalidMapException;
import com.trialmaple.model.dto.GuessDto;
import com.trialmaple.model.dto.GuessRequestDto;
import com.trialmaple.model.entities.DailyMap;
import org.springframework.stereotype.Service;

@Service
public class GuessService {

    private final GuessServiceProvider provider;

    public GuessService(GuessServiceProvider provider) {
        this.provider = provider;
    }

    /**
     * Check if a guess is correct and give hints or correct elements
     */
    public GuessDto checkGuess(DailyMap dailyMap, GuessRequestDto request) throws InvalidMapException {
        boolean isValidMap = dailyMap.getUuid().toString().equals(request.dailyMapUuid());
        if (!isValidMap) {
            // Client must refresh his page to play with the new daily map
            return new GuessDto(false);
        }
        IGuessGameModeService guessService = provider.getGuessService(dailyMap.getGameMode());
        return guessService.checkGuess(dailyMap, request);
    }
}
