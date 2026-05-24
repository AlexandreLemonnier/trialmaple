package com.trialmaple.service.guess.classic;

import com.trialmaple.model.dto.GuessDto;
import com.trialmaple.model.dto.HintPairDto;
import com.trialmaple.model.dto.WrHolderDto;
import com.trialmaple.model.entities.TmMap;
import com.trialmaple.model.enums.DeltaHint;
import com.trialmaple.model.enums.GameMode;
import com.trialmaple.repository.ScoreRepository;
import com.trialmaple.repository.TmMapRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TmnfRpgClassicGuessService extends AbstractClassicGuessService {

    public TmnfRpgClassicGuessService(TmMapRepository tmMapRepository, ScoreRepository scoreRepository) {
        super(tmMapRepository, scoreRepository);
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
