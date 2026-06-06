package com.trialmaple.service.guess.blur;

import com.trialmaple.model.dto.AnswerDto;
import com.trialmaple.model.dto.GuessDto;
import com.trialmaple.model.dto.GuessRequestDto;
import com.trialmaple.model.entities.dailymap.BlurDailyMap;
import com.trialmaple.repository.ScoreRepository;
import com.trialmaple.service.guess.AbstractGuessGameModeService;
import com.trialmaple.service.guess.GuessResult;

public abstract class AbstractBlurGuessService extends AbstractGuessGameModeService<BlurDailyMap> {

    protected AbstractBlurGuessService(ScoreRepository scoreRepository) {
        super(scoreRepository);
    }

    @Override
    protected GuessResult checkGuessInternal(BlurDailyMap dailyMap, GuessRequestDto request) {
        String dailyMapName = dailyMap.getMapName();
        String guessMapName = request.guessedMapName();

        boolean success = dailyMapName.equals(guessMapName);
        GuessDto guess = new GuessDto(true, success);

        return new GuessResult(success, guess);
    }

    @Override
    public AnswerDto getAnswer(BlurDailyMap dailyMap) {
        return new AnswerDto(dailyMap.getMapName(), null, null);
    }
}
