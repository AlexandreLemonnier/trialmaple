package com.trialmaple.service.maps.update;

import com.trialmaple.model.enums.MapList;
import com.trialmaple.model.enums.TmGame;

public interface IMapUpdateStrategy {
    /**
     * The map list where strategy applies
     */
    MapList getSupportedList();

    /**
     * The TM game where strategy applies
     */
    TmGame getSupportedGame();

    /**
     * Fetch from external API + update DB
     */
    void fetchAndUpdate();
}
