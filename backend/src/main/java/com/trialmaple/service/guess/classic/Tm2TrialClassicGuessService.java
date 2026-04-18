package com.trialmaple.service.guess.classic;

import com.trialmaple.model.dto.GuessDto;
import com.trialmaple.model.dto.HintPairDto;
import com.trialmaple.model.dto.WrHolderDto;
import com.trialmaple.model.enums.DeltaHint;
import com.trialmaple.model.enums.GameMode;
import com.trialmaple.repository.ScoreRepository;
import com.trialmaple.repository.TmMapRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Tm2TrialClassicGuessService extends AbstractClassicGuessService {

    public Tm2TrialClassicGuessService(TmMapRepository tmMapRepository, ScoreRepository scoreRepository) {
        super(tmMapRepository, scoreRepository);
    }

    @Override
    public GameMode getGameMode() {
        return GameMode.CLASSIC_TM2_TRIAL;
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
        return new GuessDto(true, success, null, points, checkpoints, finisherCount, wrTime, wrHolder, wrYear, authors, releaseYear, null);
    } 
}
