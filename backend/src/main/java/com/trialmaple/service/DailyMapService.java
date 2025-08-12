package com.trialmaple.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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

    public void chooseDailyMapIfMissing() {
        LocalDate today = LocalDate.now();

        // Period is "from yesterday 12am to today 12am"
        LocalDateTime start = today.atTime(12, 0).minusDays(1);
        LocalDateTime end = today.atTime(12, 0);

        boolean exists = dailyMapRepository.existsByDayBetween(start.toLocalDate(), end.toLocalDate());

        if (!exists) {
            List<TrialMap> allMaps = trialMapRepository.findAll();
            if (allMaps.isEmpty()) {
                throw new IllegalStateException("No trial maps available");
            }

            TrialMap randomMap = allMaps.get(new Random().nextInt(allMaps.size()));

            DailyMap dailyMap = new DailyMap(randomMap, today);

            dailyMapRepository.save(dailyMap);

            LOGGER.info("New daily map chosen: {}", randomMap.getName());
        }
    }

}