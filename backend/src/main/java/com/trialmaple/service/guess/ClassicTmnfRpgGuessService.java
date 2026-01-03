package com.trialmaple.service.guess;

import com.trialmaple.controller.mappers.TmMapDtoMapper;
import com.trialmaple.exception.InvalidMapNameException;
import com.trialmaple.model.dto.GuessDto;
import com.trialmaple.model.dto.GuessRequestDto;
import com.trialmaple.model.entities.DailyMap;
import com.trialmaple.model.enums.GameMode;
import com.trialmaple.repository.ScoreRepository;
import com.trialmaple.repository.TmMapRepository;

public class ClassicTmnfRpgGuessService extends AbstractGuessService {

    public ClassicTmnfRpgGuessService(TmMapRepository tmMapRepository, ScoreRepository scoreRepository, TmMapDtoMapper tmMapDtoMapper) {
        super(tmMapRepository, scoreRepository, tmMapDtoMapper);
    }

    @Override
    public GameMode getGameMode() {
        return GameMode.CLASSIC_TMNF_RPG;
    }

    @Override
    public GuessDto checkGuess(DailyMap dailyMap, GuessRequestDto request) throws InvalidMapNameException {
        // TODO implementation and refacto with others GuessServices
        return null;
    }
}
