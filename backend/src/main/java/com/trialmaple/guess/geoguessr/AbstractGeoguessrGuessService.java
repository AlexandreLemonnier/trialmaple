package com.trialmaple.guess.geoguessr;

import com.trialmaple.guess.dto.AnswerDto;
import com.trialmaple.guess.dto.GuessDto;
import com.trialmaple.guess.dto.GuessRequestDto;
import com.trialmaple.dailymap.geoguessr.GeoguessrDailyMap;
import com.trialmaple.score.ScoreService;
import com.trialmaple.guess.AbstractGuessGameModeService;
import com.trialmaple.guess.dto.GuessResult;

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
