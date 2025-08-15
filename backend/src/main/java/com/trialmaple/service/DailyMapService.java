package com.trialmaple.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.trialmaple.exception.NoDailyMapFoundException;
import com.trialmaple.model.entities.DailyMap;
import com.trialmaple.model.entities.TrialMap;
import com.trialmaple.repository.DailyMapRepository;
import com.trialmaple.repository.TrialMapRepository;

@Service
public class DailyMapService {
    private final TrialMapRepository trialMapRepository;
    private final DailyMapRepository dailyMapRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(DailyMapService.class);

    public DailyMapService(TrialMapRepository trialMapRepository, DailyMapRepository dailyMapRepository) {
        this.trialMapRepository = trialMapRepository;
        this.dailyMapRepository = dailyMapRepository;
    }

    public DailyMap getCurrentDailyMap() throws NoDailyMapFoundException {
        LocalDateTime currentPeriodStart = getCurrentPeriodStart();
        return dailyMapRepository.findByDay(currentPeriodStart)
                .orElseThrow(() -> new NoDailyMapFoundException(currentPeriodStart));
    }

    public void chooseDailyMapIfMissing() {
        LocalDateTime currentPeriodStart = getCurrentPeriodStart();

        boolean exists = dailyMapRepository.existsByDay(currentPeriodStart);

        if (!exists) {
            List<TrialMap> allMaps = trialMapRepository.findAll();
            if (allMaps.isEmpty()) {
                LOGGER.error("No trial maps available");
                return;
            }

            TrialMap randomMap = allMaps.get(new Random().nextInt(allMaps.size()));

            DailyMap dailyMap = new DailyMap(randomMap, currentPeriodStart);

            dailyMapRepository.save(dailyMap);

            LOGGER.info("New daily map chosen for {}: {}", currentPeriodStart, randomMap.getName());
        }
    }

    // Period is "from yesterday 12am to today 12am"
    private LocalDateTime getCurrentPeriodStart() {
        LocalDateTime now = LocalDateTime.now();
        return now.getHour() < 12
                ? now.minusDays(1).withHour(12).withMinute(0).withSecond(0).withNano(0)
                : now.withHour(12).withMinute(0).withSecond(0).withNano(0);
    }

}