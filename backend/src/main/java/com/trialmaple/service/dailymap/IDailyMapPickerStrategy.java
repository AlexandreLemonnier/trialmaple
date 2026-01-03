package com.trialmaple.service.dailymap;

import java.util.List;

import com.trialmaple.model.entities.TmMap;
import com.trialmaple.model.enums.GameMode;

public interface IDailyMapPickerStrategy {
    /**
     * The game mode where strategy applies
     */
    GameMode getSupportedGameMode();

    /**
     * Get the maps to choose from
     */
    List<TmMap> getMapPool();
}