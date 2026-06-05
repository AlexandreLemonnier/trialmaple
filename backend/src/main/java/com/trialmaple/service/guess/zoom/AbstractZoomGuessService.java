package com.trialmaple.service.guess.zoom;

import com.trialmaple.exception.InvalidMapException;
import com.trialmaple.model.dto.AnswerDto;
import com.trialmaple.model.dto.GuessDto;
import com.trialmaple.model.dto.GuessRequestDto;
import com.trialmaple.model.entities.Score;
import com.trialmaple.model.entities.User;
import com.trialmaple.model.entities.dailymap.ZoomDailyMap;
import com.trialmaple.repository.ScoreRepository;
import com.trialmaple.service.guess.IGuessGameModeService;

public abstract class AbstractZoomGuessService implements IGuessGameModeService<ZoomDailyMap> {

    private final ScoreRepository scoreRepository;

    protected AbstractZoomGuessService(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    @Override
    public GuessDto checkGuess(ZoomDailyMap dailyMap, GuessRequestDto request, User user) throws InvalidMapException {
        String dailyMapName = dailyMap.getMapName();
        String guessMapName = request.guessedMapName();

        boolean success = dailyMapName.equals(guessMapName);
        // Save score if success
        if (success) {
            Score score = new Score(request.guessNumber(), dailyMap, user);
            scoreRepository.save(score);
        }
        return new GuessDto(true, success);
    }

    @Override
    public AnswerDto getAnswer(ZoomDailyMap dailyMap) {
        return new AnswerDto(dailyMap.getMapName(), null, null);
    }
}
