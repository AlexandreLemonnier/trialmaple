package com.trialmaple.jobs;

import com.trialmaple.config.CacheName;
import com.trialmaple.service.dailymap.DailyMapService;
import com.trialmaple.service.maps.TmMapService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DailyMapScheduler {

    private final DailyMapService dailyMapService;
    private final TmMapService tmMapService;

    public DailyMapScheduler(DailyMapService dailyMapService, TmMapService tmMapService) {
        this.dailyMapService = dailyMapService;
        this.tmMapService = tmMapService;
    }

    @Scheduled(cron = "0 0 0 * * *", zone = "Europe/Paris") // Every day at midnight
    @CacheEvict(value = CacheName.DAILY_STATS, allEntries = true)
    public void scheduledDailyMapSelection() {
        tmMapService.fetchAndUpdateMaps();
        dailyMapService.pickAllDailyMapsIfMissing();
    }
}
