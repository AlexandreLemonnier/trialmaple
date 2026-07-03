package com.trialmaple.guess.classic;

import com.trialmaple.guess.dto.GuessDto;
import com.trialmaple.guess.dto.HintPairDto;
import com.trialmaple.tmmap.dto.WrHolderDto;
import com.trialmaple.tmmap.TmMap;
import com.trialmaple.guess.DeltaHint;
import com.trialmaple.core.GameMode;
import com.trialmaple.tmmap.TmMapRepository;
import com.trialmaple.score.ScoreService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TmnfRpgClassicGuessService extends AbstractClassicGuessService {

    public TmnfRpgClassicGuessService(TmMapRepository tmMapRepository, ScoreService scoreService) {
        super(tmMapRepository, scoreService);
    }

    @Override
    public GameMode getGameMode() {
        return GameMode.CLASSIC_TMNF_RPG;
    }

    @Override
    protected GuessDto computeGuess(boolean success, TmMap mapOfTheDay, TmMap guessMap) {
        HintPairDto<Integer, DeltaHint> points = computeMapPointsHint(mapOfTheDay, guessMap);
        HintPairDto<Integer, DeltaHint> checkpoints = computeMapCheckpointsHint(mapOfTheDay, guessMap);
        HintPairDto<Integer, DeltaHint> finisherCount = computeMapFinisherCountHint(mapOfTheDay, guessMap);
        HintPairDto<String, DeltaHint> wrTime = computeMapWrTimeHint(mapOfTheDay, guessMap);
        HintPairDto<WrHolderDto, Boolean> wrHolder = computeMapWrHolderHint(mapOfTheDay, guessMap);
        HintPairDto<Integer, DeltaHint> wrYear = computeMapWrYearHint(mapOfTheDay, guessMap);
        List<HintPairDto<String, Boolean>> authors = computeMapAuthorsHint(mapOfTheDay, guessMap);
        HintPairDto<Integer, DeltaHint> releaseYear = computeMapReleaseYearHint(mapOfTheDay, guessMap);
        return new GuessDto(true, success, null, points, checkpoints, finisherCount, wrTime, wrHolder, wrYear, authors, releaseYear, null);
    }
}
