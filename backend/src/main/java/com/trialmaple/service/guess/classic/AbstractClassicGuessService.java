package com.trialmaple.service.guess.classic;

import com.trialmaple.exception.InvalidMapException;
import com.trialmaple.model.dto.*;
import com.trialmaple.model.entities.dailymap.ClassicDailyMap;
import com.trialmaple.model.entities.Score;
import com.trialmaple.model.entities.TmMap;
import com.trialmaple.model.enums.DeltaHint;
import com.trialmaple.model.enums.DifficultyCategory;
import com.trialmaple.repository.ScoreRepository;
import com.trialmaple.repository.TmMapRepository;
import com.trialmaple.service.guess.IGuessGameModeService;
import com.trialmaple.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class AbstractClassicGuessService implements IGuessGameModeService<ClassicDailyMap> {

    private final TmMapRepository tmMapRepository;
    private final ScoreRepository scoreRepository;

    protected AbstractClassicGuessService(TmMapRepository tmMapRepository, ScoreRepository scoreRepository) {
        this.tmMapRepository = tmMapRepository;
        this.scoreRepository = scoreRepository;
    }

    protected abstract GuessDto computeGuess(boolean success, TmMap mapOfTheDay, TmMap guessMap);

    @Override
    public GuessDto checkGuess(ClassicDailyMap dailyMap, GuessRequestDto request) {
        TmMap mapOfTheDay = dailyMap.getMap();
        TmMap guessMap = tmMapRepository
                .findByUuid(UUID.fromString(request.guessedMapUuid()))
                .orElseThrow(() -> new InvalidMapException(request.guessedMapUuid()));

        boolean success = mapOfTheDay.getUuid().equals(guessMap.getUuid());
        // Save score if success
        if (success) {
            Score score = new Score(request.guessNumber(), dailyMap);
            scoreRepository.save(score);
        }
        return computeGuess(success, mapOfTheDay, guessMap);
    }

    @Override
    public AnswerDto getAnswer(ClassicDailyMap dailyMap) {
        TmMap mapOfTheDay = dailyMap.getMap();
        GuessDto answerGuess = computeGuess(false, mapOfTheDay, mapOfTheDay);
        return new AnswerDto(dailyMap.getMapName(), mapOfTheDay.getUuid().toString(), answerGuess);
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

    protected HintPairDto<DifficultyCategory, Boolean> computeMapDifficultyHint(TmMap mapOfTheDay, TmMap guessMap) {
        boolean isCorrectDifficulty = mapOfTheDay.getDifficulty().equals(guessMap.getDifficulty());
        return new HintPairDto<>(guessMap.getDifficulty(), isCorrectDifficulty);
    }

    protected HintPairDto<Integer, DeltaHint> computeMapPointsHint(TmMap mapOfTheDay, TmMap guessMap) {
        DeltaHint pointsDelta = compareNumber(guessMap.getPoints(), mapOfTheDay.getPoints());
        return new HintPairDto<>(guessMap.getPoints(), pointsDelta);
    }

    protected HintPairDto<Integer, DeltaHint> computeMapCheckpointsHint(TmMap mapOfTheDay, TmMap guessMap) {
        DeltaHint checkpointsDelta = compareNumber(guessMap.getCheckpointCount(), mapOfTheDay.getCheckpointCount());
        return new HintPairDto<>(guessMap.getCheckpointCount(), checkpointsDelta);
    }

    protected HintPairDto<Integer, DeltaHint> computeMapFinisherCountHint(TmMap mapOfTheDay, TmMap guessMap) {
        DeltaHint finisherCountDelta = compareNumber(guessMap.getFinisherCount(), mapOfTheDay.getFinisherCount());
        return new HintPairDto<>(guessMap.getFinisherCount(), finisherCountDelta);
    }

    protected HintPairDto<String, DeltaHint> computeMapWrTimeHint(TmMap mapOfTheDay, TmMap guessMap) {
        DeltaHint wrDelta;
        if (guessMap.getWrTime() == null && mapOfTheDay.getWrTime() == null) {
            wrDelta = DeltaHint.EQUAL;
        } else if (guessMap.getWrTime() == null || mapOfTheDay.getWrTime() == null) {
            // Can't compare a map without WR with a map with WR
            wrDelta = DeltaHint.NON_APPLICABLE;
        } else {
            wrDelta = compareNumber(guessMap.getWrTime().toMillis(), mapOfTheDay.getWrTime().toMillis());
        }
        String formattedWR = TimeUtils.formatDuration(guessMap.getWrTime());
        return new HintPairDto<>(formattedWR, wrDelta);
    }

    protected HintPairDto<WrHolderDto, Boolean> computeMapWrHolderHint(TmMap mapOfTheDay, TmMap guessMap) {
        boolean isCorrectWrHolder = mapOfTheDay.getWrHolder().getLogin().equals(guessMap.getWrHolder().getLogin());
        WrHolderDto wrHolder = new WrHolderDto(guessMap.getWrHolder().getLogin(), guessMap.getWrHolder().getDisplayName());
        return new HintPairDto<>(wrHolder, isCorrectWrHolder);
    }

    protected HintPairDto<Integer, DeltaHint> computeMapWrYearHint(TmMap mapOfTheDay, TmMap guessMap) {
        DeltaHint wrYearDelta = compareNumber(guessMap.getWrYear(), mapOfTheDay.getWrYear());
        return new HintPairDto<>(guessMap.getWrYear(), wrYearDelta);
    }

    protected List<HintPairDto<String, Boolean>> computeMapAuthorsHint(TmMap mapOfTheDay, TmMap guessMap) {
        List<HintPairDto<String, Boolean>> authors = new ArrayList<>();
        guessMap.getAuthors().forEach(author -> {
            boolean isCorrect = mapOfTheDay.getAuthors().stream().anyMatch(a -> a.equalsIgnoreCase(author));
            authors.add(new HintPairDto<>(author, isCorrect));
        });
        return authors;
    }

    protected HintPairDto<Integer, DeltaHint> computeMapReleaseYearHint(TmMap mapOfTheDay, TmMap guessMap) {
        DeltaHint releaseYearDelta = compareNumber(guessMap.getReleaseYear(), mapOfTheDay.getReleaseYear());
        return new HintPairDto<>(guessMap.getReleaseYear(), releaseYearDelta);
    }

    protected HintPairDto<Boolean, Boolean> computeMapClassicHint(TmMap mapOfTheDay, TmMap guessMap) {
        Boolean isCorrect = guessMap.isClassic() == mapOfTheDay.isClassic();
        return new HintPairDto<>(guessMap.isClassic(), isCorrect);
    }
}
