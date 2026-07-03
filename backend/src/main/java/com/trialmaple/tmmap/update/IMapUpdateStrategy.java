package com.trialmaple.tmmap.update;

import com.trialmaple.tmmap.MapList;
import com.trialmaple.core.TmGame;

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
