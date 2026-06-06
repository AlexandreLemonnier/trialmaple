package com.trialmaple.service.guess.zoom;

import com.trialmaple.model.dto.AnswerDto;
import com.trialmaple.model.dto.GuessDto;
import com.trialmaple.model.dto.GuessRequestDto;
import com.trialmaple.model.entities.dailymap.ZoomDailyMap;
import com.trialmaple.repository.ScoreRepository;
import com.trialmaple.service.guess.AbstractGuessGameModeService;
import com.trialmaple.service.guess.GuessResult;

public abstract class AbstractZoomGuessService extends AbstractGuessGameModeService<ZoomDailyMap> {

    protected AbstractZoomGuessService(ScoreRepository scoreRepository) {
        super(scoreRepository);
    }

    @Override
    protected GuessResult checkGuessInternal(ZoomDailyMap dailyMap, GuessRequestDto request) {
        String dailyMapName = dailyMap.getMapName();
        String guessMapName = request.guessedMapName();

        boolean success = dailyMapName.equals(guessMapName);
        GuessDto guess = new GuessDto(true, success);

        return new GuessResult(success, guess);
    }

    @Override
    public AnswerDto getAnswer(ZoomDailyMap dailyMap) {
        return new AnswerDto(dailyMap.getMapName(), null, null);
    }
}
