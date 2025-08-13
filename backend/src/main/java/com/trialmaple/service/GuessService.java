package com.trialmaple.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.trialmaple.model.dto.GuessDto;
import com.trialmaple.model.entities.TrialMap;
import com.trialmaple.model.enums.DeltaHint;
import com.trialmaple.repository.TrialMapRepository;

@Service
public class GuessService {

    private final TrialMapRepository trialMapRepository;

    public GuessService(TrialMapRepository trialMapRepository) {
        this.trialMapRepository = trialMapRepository;
    }

    /**
     * Check if a guess is correct and give hints or correct elements
     */
    public GuessDto checkGuess(TrialMap dailyMap, String guess) {

        TrialMap guessMap = trialMapRepository
                .findByNameIgnoreCase(guess)
                .orElseThrow(() -> new IllegalArgumentException("No map found for guess " + guess));

        boolean success = dailyMap.getName().equalsIgnoreCase(guessMap.getName());

        List<String> correctAuthors = guessMap.getAuthors().stream()
                .filter(a -> dailyMap.getAuthors().contains(a))
                .toList();

        boolean isCorrectDifficulty = dailyMap.getDifficulty().equals(guessMap.getDifficulty());

        DeltaHint pointsDelta = compareNumber(guessMap.getPoints(), dailyMap.getPoints());

        DeltaHint wrDelta = compareNumber(
                guessMap.getWorldRecord().toMillis(),
                dailyMap.getWorldRecord().toMillis());

        DeltaHint finishersDelta = compareNumber(
                guessMap.getNbFinishers(),
                dailyMap.getNbFinishers());

        return new GuessDto(success, correctAuthors, isCorrectDifficulty, pointsDelta, wrDelta, finishersDelta);

        // TODO Save Score to DB if success = true
    }

    private DeltaHint compareNumber(long guessValue, long realValue) {
        if (guessValue < realValue)
            return DeltaHint.MORE;
        if (guessValue > realValue)
            return DeltaHint.LESS;
        return DeltaHint.EQUAL;
    }

}
