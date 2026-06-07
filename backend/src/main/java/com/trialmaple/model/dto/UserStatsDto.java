package com.trialmaple.model.dto;

import com.trialmaple.model.enums.GameMode;
import com.trialmaple.model.enums.TmCategory;
import com.trialmaple.model.enums.TmGame;

public record UserStatsDto(
    double averageTries,
    int winsCount,
    int dailyMapsCount,
    GameMode gameMode,
    TmGame tmGame,
    TmCategory tmCategory
){
}
