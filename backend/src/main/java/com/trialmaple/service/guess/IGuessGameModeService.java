package com.trialmaple.service.guess;

import com.trialmaple.exception.InvalidMapNameException;
import com.trialmaple.model.dto.GuessDto;
import com.trialmaple.model.dto.GuessRequestDto;
import com.trialmaple.model.entities.DailyMap;
import com.trialmaple.model.enums.GameMode;

public interface IGuessGameModeService {

    /**
     * The game mode where strategy applies
     */
    GameMode getGameMode();

    /**
     * Check if guess is scorrect for the given daily map, and returns a guessDto with hints
     */
    GuessDto checkGuess(DailyMap dailyMap, GuessRequestDto request) throws InvalidMapNameException;
}
