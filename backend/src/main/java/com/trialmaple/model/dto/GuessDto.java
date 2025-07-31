package com.trialmaple.model.dto;

import java.util.List;

import com.trialmaple.model.enums.DeltaHint;

public class GuessDto {
    private boolean success;
    private List<String> correctAuthors;
    private boolean correctDifficulty;
    private DeltaHint deltaPoints;
    private DeltaHint deltaWR;
    private DeltaHint deltaFinishers;

    public GuessDto(boolean success, List<String> correctAuthors, boolean correctDifficulty, DeltaHint deltaPoints,
            DeltaHint deltaWR, DeltaHint deltaFinishers) {
        this.success = success;
        this.correctAuthors = correctAuthors;
        this.correctDifficulty = correctDifficulty;
        this.deltaPoints = deltaPoints;
        this.deltaWR = deltaWR;
        this.deltaFinishers = deltaFinishers;
    }

    public boolean isSuccess() {
        return success;
    }

    public List<String> getCorrectAuthors() {
        return correctAuthors;
    }

    public boolean isCorrectDifficulty() {
        return correctDifficulty;
    }

    public DeltaHint getDeltaPoints() {
        return deltaPoints;
    }

    public DeltaHint getDeltaWR() {
        return deltaWR;
    }

    public DeltaHint getDeltaFinishers() {
        return deltaFinishers;
    }
}
