package com.trialmaple.jobs;

import org.springframework.stereotype.Component;

import com.trialmaple.service.dailymap.DailyMapService;

import jakarta.annotation.PostConstruct;

@Component
public class DailyMapInitializer {

    private final DailyMapService dailyMapService;

    public DailyMapInitializer(DailyMapService dailyMapService) {
        this.dailyMapService = dailyMapService;
    }

    @PostConstruct
    public void init() {
        dailyMapService.pickAllDailyMapsIfMissing();
    }
}
