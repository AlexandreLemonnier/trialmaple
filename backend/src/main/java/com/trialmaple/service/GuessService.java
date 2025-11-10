package com.trialmaple.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.trialmaple.exception.InvalidMapNameException;
import com.trialmaple.model.dto.GuessDto;
import com.trialmaple.model.entities.DailyMap;
import com.trialmaple.model.entities.Score;
import com.trialmaple.model.entities.TrialMap;
import com.trialmaple.model.enums.DeltaHint;
import com.trialmaple.repository.ScoreRepository;
import com.trialmaple.repository.TrialMapRepository;

@Service
public class GuessService {

    private final TrialMapRepository trialMapRepository;
    private final ScoreRepository scoreRepository;

    public GuessService(TrialMapRepository trialMapRepository, ScoreRepository scoreRepository) {
        this.trialMapRepository = trialMapRepository;
        this.scoreRepository = scoreRepository;
    }

    /**
     * Check if a guess is correct and give hints or correct elements
     */
    public GuessDto checkGuess(DailyMap dailyMap, String guess, int guessNumber) throws InvalidMapNameException {

        TrialMap mapOfTheDay = dailyMap.getMap();
        TrialMap guessMap = trialMapRepository
                .findByNameIgnoreCase(guess)
                .orElseThrow(() -> new InvalidMapNameException(guess));

        boolean success = mapOfTheDay.getName().equalsIgnoreCase(guessMap.getName());

        List<String> correctAuthors = guessMap.getAuthors().stream()
                .filter(a -> mapOfTheDay.getAuthors().contains(a))
                .toList();

        boolean isCorrectDifficulty = mapOfTheDay.getDifficulty().equals(guessMap.getDifficulty());

        DeltaHint pointsDelta = compareNumber(guessMap.getPoints(), mapOfTheDay.getPoints());

        DeltaHint wrDelta = null;
        if (guessMap.getWorldRecord() == null && mapOfTheDay.getWorldRecord() == null) {
            wrDelta = DeltaHint.EQUAL;
        } else if (guessMap.getWorldRecord() == null || mapOfTheDay.getWorldRecord() == null) {
            // Can't compare a map without WR with a map with WR
            wrDelta = DeltaHint.NON_APPLICABLE;
        } else {
            wrDelta = compareNumber(guessMap.getWorldRecord().toMillis(), mapOfTheDay.getWorldRecord().toMillis());
        }

        DeltaHint finishersDelta = compareNumber(
                guessMap.getNbFinishers(),
                mapOfTheDay.getNbFinishers());

        // Save score if success
        if (success) {
            Score score = new Score(guessNumber, dailyMap);
            scoreRepository.save(score);
        }

        return new GuessDto(success, correctAuthors, isCorrectDifficulty, pointsDelta, wrDelta, finishersDelta);
    }

    private DeltaHint compareNumber(long guessValue, long realValue) {
        if (guessValue < realValue)
            return DeltaHint.MORE;
        if (guessValue > realValue)
            return DeltaHint.LESS;
        return DeltaHint.EQUAL;
    }

}
