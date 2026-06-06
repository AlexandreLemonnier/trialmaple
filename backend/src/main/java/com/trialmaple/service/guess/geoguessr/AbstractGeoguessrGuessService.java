package com.trialmaple.service.guess.geoguessr;

import com.trialmaple.model.dto.AnswerDto;
import com.trialmaple.model.dto.GuessDto;
import com.trialmaple.model.dto.GuessRequestDto;
import com.trialmaple.model.entities.dailymap.GeoguessrDailyMap;
import com.trialmaple.service.ScoreService;
import com.trialmaple.service.guess.AbstractGuessGameModeService;
import com.trialmaple.service.guess.GuessResult;

public abstract class AbstractGeoguessrGuessService extends AbstractGuessGameModeService<GeoguessrDailyMap> {

    protected AbstractGeoguessrGuessService(ScoreService scoreService) {
        super(scoreService);
    }

    @Override
    protected GuessResult checkGuessInternal(GeoguessrDailyMap dailyMap, GuessRequestDto request) {
        String dailyMapName = dailyMap.getMapName();
        String guessMapName = request.guessedMapName();

        boolean success = dailyMapName.equals(guessMapName);
        GuessDto guess = new GuessDto(true, success);

        return new GuessResult(success, guess);
    }

    @Override
    public AnswerDto getAnswer(GeoguessrDailyMap dailyMap) {
        return new AnswerDto(dailyMap.getMapName(), null, null);
    }
}
