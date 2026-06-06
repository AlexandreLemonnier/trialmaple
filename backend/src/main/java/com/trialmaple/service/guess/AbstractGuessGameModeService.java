package com.trialmaple.service.guess;

import com.trialmaple.exception.InvalidMapException;
import com.trialmaple.model.dto.AnswerDto;
import com.trialmaple.model.dto.GuessDto;
import com.trialmaple.model.dto.GuessRequestDto;
import com.trialmaple.model.entities.User;
import com.trialmaple.model.entities.dailymap.DailyMap;
import com.trialmaple.model.enums.GameMode;
import com.trialmaple.service.ScoreService;

public abstract class AbstractGuessGameModeService<T extends DailyMap> {

    protected final ScoreService scoreService;

    protected AbstractGuessGameModeService(ScoreService scoreService) {
        this.scoreService = scoreService;
    }
    /**
     * The game mode where strategy applies
     */
    protected abstract GameMode getGameMode();

    /**
     * Get answer guessDto
     */
    protected abstract AnswerDto getAnswer(T dailyMap);

    /**
     * Check if guess is correct and compute the Guess response with hints
     */
    protected abstract GuessResult checkGuessInternal(T dailyMap, GuessRequestDto request);

    /**
     * Check if guess is correct for the given daily map, and returns a guessDto with hints
     */
    public GuessDto checkGuess(T dailyMap, GuessRequestDto request, User user) throws InvalidMapException {
        GuessResult guessResult = checkGuessInternal(dailyMap, request);

        if (guessResult.success()) {
            scoreService.saveScore(dailyMap, request.guessNumber(), user);
        }

        return guessResult.guess();
    };

}
