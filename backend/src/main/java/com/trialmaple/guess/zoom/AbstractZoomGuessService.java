package com.trialmaple.guess.zoom;

import com.trialmaple.guess.dto.AnswerDto;
import com.trialmaple.guess.dto.GuessDto;
import com.trialmaple.guess.dto.GuessRequestDto;
import com.trialmaple.dailymap.zoom.ZoomDailyMap;
import com.trialmaple.score.ScoreService;
import com.trialmaple.guess.AbstractGuessGameModeService;
import com.trialmaple.guess.dto.GuessResult;

public abstract class AbstractZoomGuessService extends AbstractGuessGameModeService<ZoomDailyMap> {

    protected AbstractZoomGuessService(ScoreService scoreService) {
        super(scoreService);
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
