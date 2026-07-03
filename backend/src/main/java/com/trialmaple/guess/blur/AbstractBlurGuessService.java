package com.trialmaple.guess.blur;

import com.trialmaple.guess.dto.AnswerDto;
import com.trialmaple.guess.dto.GuessDto;
import com.trialmaple.guess.dto.GuessRequestDto;
import com.trialmaple.dailymap.blur.BlurDailyMap;
import com.trialmaple.score.ScoreService;
import com.trialmaple.guess.AbstractGuessGameModeService;
import com.trialmaple.guess.dto.GuessResult;

public abstract class AbstractBlurGuessService extends AbstractGuessGameModeService<BlurDailyMap> {

    protected AbstractBlurGuessService(ScoreService scoreService) {
        super(scoreService);
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
