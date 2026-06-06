package com.trialmaple.service.guess;

import com.trialmaple.exception.InvalidMapException;
import com.trialmaple.model.dto.AnswerDto;
import com.trialmaple.model.dto.GuessDto;
import com.trialmaple.model.dto.GuessRequestDto;
import com.trialmaple.model.entities.Score;
import com.trialmaple.model.entities.User;
import com.trialmaple.model.entities.dailymap.DailyMap;
import com.trialmaple.model.enums.GameMode;
import com.trialmaple.repository.ScoreRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractGuessGameModeService<T extends DailyMap> {

    protected final ScoreRepository scoreRepository;

    protected AbstractGuessGameModeService(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
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
            if (user != null && scoreRepository.findByDailyMapAndUser(dailyMap, user).isPresent()) {
                // A player can only have 1 score for each daily map
                log.warn("Player {} already has a score for daily map {} ({})", user.getUsername(), dailyMap.getMapName(), dailyMap.getId());
            } else {
                Score score = new Score(request.guessNumber(), dailyMap, user);
                scoreRepository.save(score);
            }
        }

        return guessResult.guess();
    };

}
