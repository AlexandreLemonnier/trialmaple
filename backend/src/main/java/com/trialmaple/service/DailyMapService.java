package com.trialmaple.service;

import java.time.LocalDate;
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
        LocalDate today = LocalDate.now();
        return dailyMapRepository.findByDay(today)
                .orElseThrow(() -> new NoDailyMapFoundException(today));
    }

    public void chooseDailyMapIfMissing() {
        LocalDate today = LocalDate.now();

        boolean exists = dailyMapRepository.existsByDay(today);

        if (!exists) {
            List<TrialMap> allMaps = trialMapRepository.findAll();
            if (allMaps.isEmpty()) {
                LOGGER.error("No trial maps available");
                return;
            }

            TrialMap randomMap = allMaps.get(new Random().nextInt(allMaps.size()));

            DailyMap dailyMap = new DailyMap(randomMap, today);

            dailyMapRepository.save(dailyMap);

            LOGGER.info("New daily map chosen for {}: {}", today, randomMap.getName());
        }
    }

}