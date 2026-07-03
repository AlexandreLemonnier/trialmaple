package com.trialmaple.statistics;

import com.trialmaple.core.GameMode;
import com.trialmaple.core.TmCategory;
import com.trialmaple.core.TmGame;

public record UserStatsDto(
    double averageTries,
    int winsCount,
    int dailyMapsCount,
    GameMode gameMode,
    TmGame tmGame,
    TmCategory tmCategory
){
}
