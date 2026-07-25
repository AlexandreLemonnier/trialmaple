package com.trialmaple.dailymap.jobs;

import com.trialmaple.dailymap.DailyMapService;
import com.trialmaple.tmmap.TmMapService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DailyMapInitializer {

    private final DailyMapService dailyMapService;
    private final TmMapService tmMapService;

    @EventListener(ApplicationReadyEvent.class)
    @Async
    public void init() {
        tmMapService.fetchAndUpdateMaps();
        dailyMapService.pickAllDailyMapsIfMissing();
    }
}
