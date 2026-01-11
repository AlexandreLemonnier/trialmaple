package com.trialmaple.jobs;

import org.springframework.stereotype.Component;

import com.trialmaple.service.dailymap.DailyMapService;
import com.trialmaple.service.maps.TmMapService;

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
        dailyMapService.pickAllDailyMapsIfMissing();
        tmMapService.fetchAndUpdateMaps();
    }
}
