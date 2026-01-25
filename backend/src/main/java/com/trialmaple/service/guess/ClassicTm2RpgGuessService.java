package com.trialmaple.service.guess;

import java.util.List;

import org.springframework.stereotype.Service;

import com.trialmaple.model.dto.GuessDto;
import com.trialmaple.model.dto.HintPairDto;
import com.trialmaple.model.dto.WrHolderDto;
import com.trialmaple.model.enums.DeltaHint;
import com.trialmaple.model.enums.GameMode;
import com.trialmaple.repository.ScoreRepository;
import com.trialmaple.repository.TmMapRepository;

@Service
public class ClassicTm2RpgGuessService extends AbstractGuessService {

    public ClassicTm2RpgGuessService(TmMapRepository tmMapRepository, ScoreRepository scoreRepository) {
        super(tmMapRepository, scoreRepository);
    }

    @Override
    public GameMode getGameMode() {
        return GameMode.CLASSIC_TM2_RPG;
    }

    @Override
    protected GuessDto computeGuess(boolean success) {
        HintPairDto<Integer, DeltaHint> points = computeMapPointsHint();
        HintPairDto<Integer, DeltaHint> checkpoints = computeMapCheckpointsHint();
        HintPairDto<Integer, DeltaHint> finisherCount = computeMapFinisherCountHint();
        HintPairDto<String, DeltaHint> wrTime = computeMapWrTimeHint();
        HintPairDto<WrHolderDto, Boolean> wrHolder = computeMapWrHolderHint();
        HintPairDto<Integer, DeltaHint> wrYear = computeMapWrYearHint();
        List<HintPairDto<String, Boolean>> authors = computeMapAuthorsHint();
        HintPairDto<Integer, DeltaHint> releaseYear = computeMapReleaseYearHint();
        HintPairDto<Boolean, Boolean> classic = computeMapClassicHint();
        return new GuessDto(true, success, null, points, checkpoints, finisherCount, wrTime, wrHolder, wrYear, authors, releaseYear, classic);
    } 
}
