package com.trialmaple.dailymap.jobs;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.trialmaple.dailymap.DailyMapService;
import com.trialmaple.tmmap.TmMapService;

import jakarta.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class DailyMapInitializer {

    private final DailyMapService dailyMapService;
    private final TmMapService tmMapService;

    @PostConstruct
    public void init() {
        tmMapService.fetchAndUpdateMaps();
        dailyMapService.pickAllDailyMapsIfMissing();
    }
}
