package com.trialmaple.guess.classic;

import com.trialmaple.core.exception.InvalidMapException;
import com.trialmaple.guess.*;
import com.trialmaple.guess.dto.*;
import com.trialmaple.tmmap.TmMap;
import com.trialmaple.dailymap.classic.ClassicDailyMap;
import com.trialmaple.guess.DeltaHint;
import com.trialmaple.tmmap.DifficultyCategory;
import com.trialmaple.tmmap.TmMapRepository;
import com.trialmaple.score.ScoreService;
import com.trialmaple.core.utils.TimeUtils;
import com.trialmaple.tmmap.dto.WrHolderDto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class AbstractClassicGuessService extends AbstractGuessGameModeService<ClassicDailyMap> {

    private final TmMapRepository tmMapRepository;

    protected AbstractClassicGuessService(TmMapRepository tmMapRepository, ScoreService scoreService) {
        super(scoreService);
        this.tmMapRepository = tmMapRepository;
    }

    protected abstract GuessDto computeGuess(boolean success, TmMap mapOfTheDay, TmMap guessMap);

    @Override
    protected GuessResult checkGuessInternal(ClassicDailyMap dailyMap, GuessRequestDto request) {
        TmMap mapOfTheDay = dailyMap.getMap();
        TmMap guessMap = tmMapRepository
                .findByUuid(UUID.fromString(request.guessedMapUuid()))
                .orElseThrow(() -> new InvalidMapException(request.guessedMapUuid()));

        boolean success = mapOfTheDay.getUuid().equals(guessMap.getUuid());
        GuessDto guess = computeGuess(success, dailyMap.getMap(), guessMap);

        return new GuessResult(success, guess);
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
