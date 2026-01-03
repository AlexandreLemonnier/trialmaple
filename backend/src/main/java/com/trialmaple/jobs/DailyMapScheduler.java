package com.trialmaple.jobs;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.trialmaple.service.dailymap.DailyMapService;
import com.trialmaple.service.maps.TmMapService;

@Component
public class DailyMapScheduler {

    private final DailyMapService dailyMapService;
    private final TmMapService tmMapService;

    public DailyMapScheduler(DailyMapService dailyMapService, TmMapService tmMapService) {
        this.dailyMapService = dailyMapService;
        this.tmMapService = tmMapService;
    }

    @Scheduled(cron = "0 0 0 * * *", zone = "Europe/Paris") // Everyday at midnight
    public void scheduledDailyMapSelection() {
        dailyMapService.pickAllDailyMapsIfMissing();
        tmMapService.fetchAndUpdateMaps();
    }
}
