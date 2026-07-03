package com.trialmaple.guess.classic;

import com.trialmaple.guess.dto.GuessDto;
import com.trialmaple.guess.dto.HintPairDto;
import com.trialmaple.tmmap.dto.WrHolderDto;
import com.trialmaple.tmmap.TmMap;
import com.trialmaple.guess.DeltaHint;
import com.trialmaple.tmmap.DifficultyCategory;
import com.trialmaple.core.GameMode;
import com.trialmaple.tmmap.TmMapRepository;
import com.trialmaple.score.ScoreService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TmnfTrialClassicGuessService extends AbstractClassicGuessService {

    public TmnfTrialClassicGuessService(TmMapRepository tmMapRepository, ScoreService scoreService) {
        super(tmMapRepository, scoreService);
    }

    @Override
    public GameMode getGameMode() {
        return GameMode.CLASSIC_TMNF_TRIAL;
    }

    @Override
    protected GuessDto computeGuess(boolean success, TmMap mapOfTheDay, TmMap guessMap) {
        HintPairDto<DifficultyCategory, Boolean> difficulty = computeMapDifficultyHint(mapOfTheDay, guessMap);
        HintPairDto<Integer, DeltaHint> points = computeMapPointsHint(mapOfTheDay, guessMap);
        HintPairDto<Integer, DeltaHint> checkpoints = computeMapCheckpointsHint(mapOfTheDay, guessMap);
        HintPairDto<Integer, DeltaHint> finisherCount = computeMapFinisherCountHint(mapOfTheDay, guessMap);
        HintPairDto<String, DeltaHint> wrTime = computeMapWrTimeHint(mapOfTheDay, guessMap);
        HintPairDto<WrHolderDto, Boolean> wrHolder = computeMapWrHolderHint(mapOfTheDay, guessMap);
        List<HintPairDto<String, Boolean>> authors = computeMapAuthorsHint(mapOfTheDay, guessMap);
        HintPairDto<Integer, DeltaHint> releaseYear = computeMapReleaseYearHint(mapOfTheDay, guessMap);
        return new GuessDto(true, success, difficulty, points, checkpoints, finisherCount, wrTime, wrHolder, null, authors, releaseYear, null);
    }    
}
