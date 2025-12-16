package com.trialmaple.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.trialmaple.controller.mappers.TrialMapDtoMapper;
import com.trialmaple.exception.InvalidMapNameException;
import com.trialmaple.model.dto.GuessDto;
import com.trialmaple.model.dto.GuessRequestDto;
import com.trialmaple.model.dto.HintPairDto;
import com.trialmaple.model.entities.DailyMap;
import com.trialmaple.model.entities.Score;
import com.trialmaple.model.entities.TrialMap;
import com.trialmaple.model.enums.DeltaHint;
import com.trialmaple.model.enums.DifficultyCategory;
import com.trialmaple.repository.ScoreRepository;
import com.trialmaple.repository.TrialMapRepository;

@Service
public class GuessService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GuessService.class);

    private final TrialMapRepository trialMapRepository;
    private final ScoreRepository scoreRepository;
    private final TrialMapDtoMapper trialMapDtoMapper;

    public GuessService(TrialMapRepository trialMapRepository, ScoreRepository scoreRepository,
            TrialMapDtoMapper trialMapDtoMapper) {
        this.trialMapRepository = trialMapRepository;
        this.scoreRepository = scoreRepository;
        this.trialMapDtoMapper = trialMapDtoMapper;
    }

    /**
     * Check if a guess is correct and give hints or correct elements
     */
    public GuessDto checkGuess(DailyMap dailyMap, GuessRequestDto request) throws InvalidMapNameException {

        LOGGER.info("New guess: " + request.guess());

        TrialMap mapOfTheDay = dailyMap.getMap();
        TrialMap guessMap = trialMapRepository
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

        DeltaHint checkpointsDelta = compareNumber(guessMap.getNbCheckpoints(), mapOfTheDay.getNbCheckpoints());
        HintPairDto<Integer, DeltaHint> checkpoints = new HintPairDto<Integer, DeltaHint>(guessMap.getNbCheckpoints(),
                checkpointsDelta);

        DeltaHint nbFinishersDelta = compareNumber(guessMap.getNbFinishers(), mapOfTheDay.getNbFinishers());
        HintPairDto<Integer, DeltaHint> nbFinishers = new HintPairDto<Integer, DeltaHint>(guessMap.getNbFinishers(),
                nbFinishersDelta);

        DeltaHint wrDelta = null;
        if (guessMap.getWorldRecord() == null && mapOfTheDay.getWorldRecord() == null) {
            wrDelta = DeltaHint.EQUAL;
        } else if (guessMap.getWorldRecord() == null || mapOfTheDay.getWorldRecord() == null) {
            // Can't compare a map without WR with a map with WR
            wrDelta = DeltaHint.NON_APPLICABLE;
        } else {
            wrDelta = compareNumber(guessMap.getWorldRecord().toMillis(), mapOfTheDay.getWorldRecord().toMillis());
        }
        String formattedWR = trialMapDtoMapper.serviceToDto(guessMap).worldRecord();
        HintPairDto<String, DeltaHint> worldRecord = new HintPairDto<String, DeltaHint>(formattedWR, wrDelta);

        List<HintPairDto<String, Boolean>> authors = new ArrayList<>();
        guessMap.getAuthors().forEach((author) -> {
            boolean isCorrect = mapOfTheDay.getAuthors().contains(author);
            authors.add(new HintPairDto<String, Boolean>(author, isCorrect));
        });

        // Save score if success
        if (success) {
            Score score = new Score(request.guessNumber(), dailyMap);
            scoreRepository.save(score);
        }

        return new GuessDto(true, success, difficulty, points, checkpoints, nbFinishers, worldRecord, authors);
    }

    private DeltaHint compareNumber(long guessValue, long realValue) {
        if (guessValue < realValue)
            return DeltaHint.MORE;
        if (guessValue > realValue)
            return DeltaHint.LESS;
        return DeltaHint.EQUAL;
    }

}
