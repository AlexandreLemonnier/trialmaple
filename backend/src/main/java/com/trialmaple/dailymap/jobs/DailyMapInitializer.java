package com.trialmaple.dailymap.jobs;

import org.springframework.stereotype.Component;

import com.trialmaple.dailymap.DailyMapService;
import com.trialmaple.tmmap.TmMapService;

import jakarta.annotation.PostConstruct;

@Component
public class DailyMapInitializer {

    private final DailyMapService dailyMapService;
        private final TmMapService tmMapService;

    public DailyMapInitializer(DailyMapService dailyMapService, TmMapService tmMapService) {
        this.dailyMapService = dailyMapService;
        this.tmMapService = tmMapService;
    }

    @PostConstruct
    public void init() {
        tmMapService.fetchAndUpdateMaps();
        dailyMapService.pickAllDailyMapsIfMissing();
    }
}
