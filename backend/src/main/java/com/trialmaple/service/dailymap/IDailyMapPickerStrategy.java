package com.trialmaple.service.dailymap;

import com.trialmaple.model.entities.DailyMap;
import com.trialmaple.model.entities.TmMap;
import com.trialmaple.model.enums.GameMode;

import java.time.LocalDate;
import java.util.List;

public interface IDailyMapPickerStrategy<T extends DailyMap> {
    /**
     * The game mode where strategy applies
     */
    GameMode getSupportedGameMode();

    /**
     * Get the maps to choose from
     */
    List<TmMap> getMapPool();

    /**
     * Randomly pick a daily map
     */
    T pickDailyMap(LocalDate day);
}