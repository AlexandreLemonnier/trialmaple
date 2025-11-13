package com.trialmaple.model.dto;

import java.util.List;

import com.trialmaple.model.enums.DeltaHint;
import com.trialmaple.model.enums.DifficultyCategory;

public record GuessDto(
        boolean success,
        HintPairDto<DifficultyCategory, Boolean> difficulty,
        HintPairDto<Integer, DeltaHint> points,
        HintPairDto<Integer, DeltaHint> checkpoints,
        HintPairDto<Integer, DeltaHint> nbFinishers,
        HintPairDto<String, DeltaHint> worldRecord,
        List<HintPairDto<String, Boolean>> authors
) {
}
