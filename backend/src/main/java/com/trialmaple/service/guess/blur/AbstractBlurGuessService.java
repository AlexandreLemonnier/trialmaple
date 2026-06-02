package com.trialmaple.service.guess.blur;

import com.trialmaple.exception.InvalidMapException;
import com.trialmaple.model.dto.AnswerDto;
import com.trialmaple.model.dto.GuessDto;
import com.trialmaple.model.dto.GuessRequestDto;
import com.trialmaple.model.entities.BlurDailyMap;
import com.trialmaple.model.entities.Score;
import com.trialmaple.repository.ScoreRepository;
import com.trialmaple.service.guess.IGuessGameModeService;

public abstract class AbstractBlurGuessService implements IGuessGameModeService<BlurDailyMap> {

    private final ScoreRepository scoreRepository;

    protected AbstractBlurGuessService(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    @Override
    public GuessDto checkGuess(BlurDailyMap dailyMap, GuessRequestDto request) throws InvalidMapException {
        String dailyMapName = dailyMap.getMapName();
        String guessMapName = request.guessedMapName();

        boolean success = dailyMapName.equals(guessMapName);
        // Save score if success
        if (success) {
            Score score = new Score(request.guessNumber(), dailyMap);
            scoreRepository.save(score);
        }
        return new GuessDto(true, success);
    }

    @Override
    public AnswerDto getAnswer(BlurDailyMap dailyMap) {
        return new AnswerDto(dailyMap.getMapName(), null, null);
    }
}
