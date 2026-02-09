package com.trialmaple.service.guess;

import java.util.List;

import org.springframework.stereotype.Service;

import com.trialmaple.model.dto.GuessDto;
import com.trialmaple.model.dto.HintPairDto;
import com.trialmaple.model.dto.WrHolderDto;
import com.trialmaple.model.enums.DeltaHint;
import com.trialmaple.model.enums.DifficultyCategory;
import com.trialmaple.model.enums.GameMode;
import com.trialmaple.repository.ScoreRepository;
import com.trialmaple.repository.TmMapRepository;

@Service
public class ClassicTmnfTrialGuessService extends AbstractGuessService {

    public ClassicTmnfTrialGuessService(TmMapRepository tmMapRepository, ScoreRepository scoreRepository) {
        super(tmMapRepository, scoreRepository);
    }

    @Override
    public GameMode getGameMode() {
        return GameMode.CLASSIC_TMNF_TRIAL;
    }

    @Override
    protected GuessDto computeGuess(boolean success) {
        HintPairDto<DifficultyCategory, Boolean> difficulty = computeMapDifficultyHint();
        HintPairDto<Integer, DeltaHint> points = computeMapPointsHint();
        HintPairDto<Integer, DeltaHint> checkpoints = computeMapCheckpointsHint();
        HintPairDto<Integer, DeltaHint> finisherCount = computeMapFinisherCountHint();
        HintPairDto<String, DeltaHint> wrTime = computeMapWrTimeHint();
        HintPairDto<WrHolderDto, Boolean> wrHolder = computeMapWrHolderHint();
        List<HintPairDto<String, Boolean>> authors = computeMapAuthorsHint();
        HintPairDto<Integer, DeltaHint> releaseYear = computeMapReleaseYearHint();
        return new GuessDto(true, success, difficulty, points, checkpoints, finisherCount, wrTime, wrHolder, null, authors, releaseYear, null);
    }    
}
