package com.trialmaple.model.dto;

import java.util.Collections;
import java.util.List;

import com.trialmaple.model.enums.DeltaHint;
import com.trialmaple.model.enums.DifficultyCategory;

public record GuessDto(
        boolean isValidDay,
        boolean success,
        HintPairDto<DifficultyCategory, Boolean> difficulty,
        HintPairDto<Integer, DeltaHint> points,
        HintPairDto<Integer, DeltaHint> checkpoints,
        HintPairDto<Integer, DeltaHint> finisherCount,
        HintPairDto<String, DeltaHint> worldRecord,
        List<HintPairDto<String, Boolean>> authors
) {
        public GuessDto(boolean isValidDay) {
               this(isValidDay, false, null, null, null, null, null, Collections.emptyList());
        }
}
