package com.trialmaple.service.guess;

import java.util.ArrayList;
import java.util.List;

import com.trialmaple.controller.mappers.TmMapDtoMapper;
import com.trialmaple.exception.InvalidMapNameException;
import com.trialmaple.model.dto.GuessDto;
import com.trialmaple.model.dto.GuessRequestDto;
import com.trialmaple.model.dto.HintPairDto;
import com.trialmaple.model.dto.WrHolderDto;
import com.trialmaple.model.entities.DailyMap;
import com.trialmaple.model.entities.Score;
import com.trialmaple.model.entities.TmMap;
import com.trialmaple.model.enums.DeltaHint;
import com.trialmaple.model.enums.DifficultyCategory;
import com.trialmaple.repository.ScoreRepository;
import com.trialmaple.repository.TmMapRepository;

public abstract class AbstractGuessService implements IGuessGameModeService {

    protected final TmMapRepository tmMapRepository;
    protected final ScoreRepository scoreRepository;
    protected final TmMapDtoMapper tmMapDtoMapper;

    protected TmMap mapOfTheDay;
    protected TmMap guessMap;

    public AbstractGuessService(TmMapRepository tmMapRepository, ScoreRepository scoreRepository, TmMapDtoMapper tmMapDtoMapper) {
        this.tmMapRepository = tmMapRepository;
        this.scoreRepository = scoreRepository;
        this.tmMapDtoMapper = tmMapDtoMapper;
    }

    protected abstract GuessDto computeGuess(boolean success);

    @Override
    public GuessDto checkGuess(DailyMap dailyMap, GuessRequestDto request) {
        this.mapOfTheDay = dailyMap.getMap();
        this.guessMap = tmMapRepository
                .findByNameIgnoreCase(request.guess())
                .orElseThrow(() -> new InvalidMapNameException(request.guess()));

        boolean isValidMap = dailyMap.getUuid().toString().equals(request.dailyMapUuid());
        if (!isValidMap) {
            // Client must refresh his page to play with the new daily map
            return new GuessDto(false);
        }

        boolean success = mapOfTheDay.getName().equalsIgnoreCase(guessMap.getName());
        // Save score if success
        if (success) {
            Score score = new Score(request.guessNumber(), dailyMap);
            scoreRepository.save(score);
        }
        return computeGuess(success);
    }

    /**
     * Compare 2 numbers and return the corresponding DeltaHint
     */
    protected DeltaHint compareNumber(long guessValue, long realValue) {
        if (guessValue < realValue)
            return DeltaHint.MORE;
        if (guessValue > realValue)
            return DeltaHint.LESS;
        return DeltaHint.EQUAL;
    }

    protected HintPairDto<DifficultyCategory, Boolean> computeMapDifficultyHint() {
        boolean isCorrectDifficulty = mapOfTheDay.getDifficulty().equals(guessMap.getDifficulty());
        return new HintPairDto<DifficultyCategory, Boolean>(
                guessMap.getDifficulty(), isCorrectDifficulty);
    }

    protected HintPairDto<Integer, DeltaHint> computeMapPointsHint() {
        DeltaHint pointsDelta = compareNumber(guessMap.getPoints(), mapOfTheDay.getPoints());
        return new HintPairDto<Integer, DeltaHint>(guessMap.getPoints(), pointsDelta);
    }

    protected HintPairDto<Integer, DeltaHint> computeMapCheckpointsHint() {
        DeltaHint checkpointsDelta = compareNumber(guessMap.getCheckpointCount(), mapOfTheDay.getCheckpointCount());
        return new HintPairDto<Integer, DeltaHint>(guessMap.getCheckpointCount(), checkpointsDelta);
    }

    protected HintPairDto<Integer, DeltaHint> computeMapFinisherCountHint() {
        DeltaHint finisherCountDelta = compareNumber(guessMap.getFinisherCount(), mapOfTheDay.getFinisherCount());
        return new HintPairDto<Integer, DeltaHint>(guessMap.getFinisherCount(), finisherCountDelta);
    }

    protected HintPairDto<String, DeltaHint> computeMapWrTimeHint() {
        DeltaHint wrDelta = null;
        if (guessMap.getWrTime() == null && mapOfTheDay.getWrTime() == null) {
            wrDelta = DeltaHint.EQUAL;
        } else if (guessMap.getWrTime() == null || mapOfTheDay.getWrTime() == null) {
            // Can't compare a map without WR with a map with WR
            wrDelta = DeltaHint.NON_APPLICABLE;
        } else {
            wrDelta = compareNumber(guessMap.getWrTime().toMillis(), mapOfTheDay.getWrTime().toMillis());
        }
        String formattedWR = tmMapDtoMapper.serviceToDto(guessMap).wrTime();
        return new HintPairDto<String, DeltaHint>(formattedWR, wrDelta);
    }

    protected HintPairDto<WrHolderDto, Boolean> computeMapWrHolderHint() {
        boolean isCorrectWrHolder = mapOfTheDay.getWrHolder().getLogin().equals(guessMap.getWrHolder().getLogin());
        WrHolderDto wrHolder = new WrHolderDto(guessMap.getWrHolder().getLogin(), guessMap.getWrHolder().getDisplayName());
        return new HintPairDto<WrHolderDto, Boolean>(wrHolder, isCorrectWrHolder);
    }

    protected HintPairDto<Integer, DeltaHint> computeMapWrYearHint() {
        DeltaHint wrYearDelta = compareNumber(guessMap.getWrYear(), mapOfTheDay.getWrYear());
        return new HintPairDto<Integer, DeltaHint>(guessMap.getWrYear(), wrYearDelta);
    };

    protected List<HintPairDto<String, Boolean>> computeMapAuthorsHint() {
        List<HintPairDto<String, Boolean>> authors = new ArrayList<>();
        guessMap.getAuthors().forEach((author) -> {
            boolean isCorrect = mapOfTheDay.getAuthors().stream().anyMatch(a -> a.equalsIgnoreCase(author));
            authors.add(new HintPairDto<String, Boolean>(author, isCorrect));
        });
        return authors;
    }

    protected HintPairDto<Integer, DeltaHint> computeMapReleaseYearHint() {
        DeltaHint releaseYearDelta = compareNumber(guessMap.getReleaseYear(), mapOfTheDay.getReleaseYear());
        return new HintPairDto<Integer, DeltaHint>(guessMap.getReleaseYear(), releaseYearDelta);
    }
}
