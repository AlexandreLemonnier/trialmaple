package com.trialmaple.jobs;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.trialmaple.service.DailyMapService;

@Component
public class DailyMapScheduler {

    private final DailyMapService dailyMapService;

    public DailyMapScheduler(DailyMapService dailyMapService) {
        this.dailyMapService = dailyMapService;
    }

    @Scheduled(cron = "0 0 0 * * *", zone = "Europe/Paris") // Everyday at midnight
    public void scheduledDailyMapSelection() {
        dailyMapService.chooseDailyMapIfMissing();
    }
}
