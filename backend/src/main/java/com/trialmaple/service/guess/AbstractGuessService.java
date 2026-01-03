package com.trialmaple.service.guess;

import com.trialmaple.controller.mappers.TmMapDtoMapper;
import com.trialmaple.model.enums.DeltaHint;
import com.trialmaple.repository.ScoreRepository;
import com.trialmaple.repository.TmMapRepository;

public abstract class AbstractGuessService implements IGuessGameModeService {

    protected final TmMapRepository tmMapRepository;
    protected final ScoreRepository scoreRepository;
    protected final TmMapDtoMapper tmMapDtoMapper;

    public AbstractGuessService(TmMapRepository tmMapRepository, ScoreRepository scoreRepository, TmMapDtoMapper tmMapDtoMapper) {
        this.tmMapRepository = tmMapRepository;
        this.scoreRepository = scoreRepository;
        this.tmMapDtoMapper = tmMapDtoMapper;
    }

    protected DeltaHint compareNumber(long guessValue, long realValue) {
        if (guessValue < realValue)
            return DeltaHint.MORE;
        if (guessValue > realValue)
            return DeltaHint.LESS;
        return DeltaHint.EQUAL;
    }
}
