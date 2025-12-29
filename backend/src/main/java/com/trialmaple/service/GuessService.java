package com.trialmaple.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.trialmaple.controller.mappers.TmMapDtoMapper;
import com.trialmaple.exception.InvalidMapNameException;
import com.trialmaple.model.dto.GuessDto;
import com.trialmaple.model.dto.GuessRequestDto;
import com.trialmaple.model.dto.HintPairDto;
import com.trialmaple.model.entities.DailyMap;
import com.trialmaple.model.entities.Score;
import com.trialmaple.model.entities.TmMap;
import com.trialmaple.model.enums.DeltaHint;
import com.trialmaple.model.enums.DifficultyCategory;
import com.trialmaple.repository.ScoreRepository;
import com.trialmaple.repository.TmMapRepository;

@Service
public class GuessService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GuessService.class);

    private final TmMapRepository tmMapRepository;
    private final ScoreRepository scoreRepository;
    private final TmMapDtoMapper tmMapDtoMapper;

    public GuessService(TmMapRepository tmMapRepository, ScoreRepository scoreRepository,
            TmMapDtoMapper tmMapDtoMapper) {
        this.tmMapRepository = tmMapRepository;
        this.scoreRepository = scoreRepository;
        this.tmMapDtoMapper = tmMapDtoMapper;
    }

    /**
     * Check if a guess is correct and give hints or correct elements
     */
    public GuessDto checkGuess(DailyMap dailyMap, GuessRequestDto request) throws InvalidMapNameException {

        LOGGER.info("New guess: " + request.guess());

        TmMap mapOfTheDay = dailyMap.getMap();
        TmMap guessMap = tmMapRepository
                .findByNameIgnoreCase(request.guess())
                .orElseThrow(() -> new InvalidMapNameException(request.guess()));

        boolean isValidMap = dailyMap.getUuid().toString().equals(request.dailyMapUuid());
        if (!isValidMap) {
            // Client must refresh his page to play on new daily map
            return new GuessDto(false);
        }

        boolean success = mapOfTheDay.getName().equalsIgnoreCase(guessMap.getName());

        boolean isCorrectDifficulty = mapOfTheDay.getDifficulty().equals(guessMap.getDifficulty());
        HintPairDto<DifficultyCategory, Boolean> difficulty = new HintPairDto<DifficultyCategory, Boolean>(
                guessMap.getDifficulty(), isCorrectDifficulty);

        DeltaHint pointsDelta = compareNumber(guessMap.getPoints(), mapOfTheDay.getPoints());
        HintPairDto<Integer, DeltaHint> points = new HintPairDto<Integer, DeltaHint>(guessMap.getPoints(), pointsDelta);

        DeltaHint checkpointsDelta = compareNumber(guessMap.getCheckpointCount(), mapOfTheDay.getCheckpointCount());
        HintPairDto<Integer, DeltaHint> checkpoints = new HintPairDto<Integer, DeltaHint>(guessMap.getCheckpointCount(),
                checkpointsDelta);

        DeltaHint finisherCountDelta = compareNumber(guessMap.getFinisherCount(), mapOfTheDay.getFinisherCount());
        HintPairDto<Integer, DeltaHint> finisherCount = new HintPairDto<Integer, DeltaHint>(guessMap.getFinisherCount(),
                finisherCountDelta);

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
        HintPairDto<String, DeltaHint> wrTime = new HintPairDto<String, DeltaHint>(formattedWR, wrDelta);

        List<HintPairDto<String, Boolean>> authors = new ArrayList<>();
        guessMap.getAuthors().forEach((author) -> {
            boolean isCorrect = mapOfTheDay.getAuthors().stream().anyMatch(a -> a.equalsIgnoreCase(author));
            authors.add(new HintPairDto<String, Boolean>(author, isCorrect));
        });

        DeltaHint releaseYearDelta = compareNumber(guessMap.getReleaseYear(), mapOfTheDay.getReleaseYear());
        HintPairDto<Integer, DeltaHint> releaseYear = new HintPairDto<Integer, DeltaHint>(guessMap.getReleaseYear(), releaseYearDelta);

        // Save score if success
        if (success) {
            Score score = new Score(request.guessNumber(), dailyMap);
            scoreRepository.save(score);
        }

        return new GuessDto(true, success, difficulty, points, checkpoints, finisherCount, wrTime, authors, releaseYear);
    }

    private DeltaHint compareNumber(long guessValue, long realValue) {
        if (guessValue < realValue)
            return DeltaHint.MORE;
        if (guessValue > realValue)
            return DeltaHint.LESS;
        return DeltaHint.EQUAL;
    }

}
