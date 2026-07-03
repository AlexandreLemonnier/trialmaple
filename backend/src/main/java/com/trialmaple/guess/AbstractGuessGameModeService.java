package com.trialmaple.guess;

import com.trialmaple.core.exception.InvalidMapException;
import com.trialmaple.guess.dto.AnswerDto;
import com.trialmaple.guess.dto.GuessDto;
import com.trialmaple.guess.dto.GuessRequestDto;
import com.trialmaple.guess.dto.GuessResult;
import com.trialmaple.user.User;
import com.trialmaple.dailymap.DailyMap;
import com.trialmaple.core.GameMode;
import com.trialmaple.score.ScoreService;

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
