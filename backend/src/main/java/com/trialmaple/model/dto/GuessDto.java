package com.trialmaple.model.dto;

import java.util.List;

import com.trialmaple.model.enums.DeltaHint;

public record GuessDto(
        boolean success,
        List<String> correctAuthors,
        boolean correctDifficulty,
        DeltaHint deltaPoints,
        DeltaHint deltaWR,
        DeltaHint deltaFinishers) {
}
