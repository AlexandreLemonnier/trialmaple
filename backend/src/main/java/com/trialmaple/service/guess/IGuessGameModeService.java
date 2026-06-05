package com.trialmaple.service.guess;

import com.trialmaple.exception.InvalidMapException;
import com.trialmaple.model.dto.AnswerDto;
import com.trialmaple.model.dto.GuessDto;
import com.trialmaple.model.dto.GuessRequestDto;
import com.trialmaple.model.entities.User;
import com.trialmaple.model.entities.dailymap.DailyMap;
import com.trialmaple.model.enums.GameMode;

public interface IGuessGameModeService<T extends DailyMap> {

    /**
     * The game mode where strategy applies
     */
    GameMode getGameMode();

    /**
     * Check if guess is correct for the given daily map, and returns a guessDto with hints
     */
    GuessDto checkGuess(T dailyMap, GuessRequestDto request, User user) throws InvalidMapException;

    /**
     * Get answer guessDto
     */
    AnswerDto getAnswer(T dailyMap);
}
