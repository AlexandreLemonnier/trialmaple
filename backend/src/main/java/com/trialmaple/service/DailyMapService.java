package com.trialmaple.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.trialmaple.exception.NoDailyMapFoundException;
import com.trialmaple.model.entities.DailyMap;
import com.trialmaple.model.entities.TmMap;
import com.trialmaple.repository.DailyMapRepository;
import com.trialmaple.repository.TmMapRepository;

@Service
public class DailyMapService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DailyMapService.class);

    private final TmMapRepository tmMapRepository;
    private final DailyMapRepository dailyMapRepository;

    public DailyMapService(TmMapRepository tmMapRepository, DailyMapRepository dailyMapRepository) {
        this.tmMapRepository = tmMapRepository;
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
            List<TmMap> allMaps = tmMapRepository.findByWrTimeIsNotNullAndActiveTrue();
            if (allMaps.isEmpty()) {
                LOGGER.error("No trial maps available");
                return;
            }

            TmMap randomMap = allMaps.get(new Random().nextInt(allMaps.size()));

            DailyMap dailyMap = new DailyMap(randomMap, today);

            dailyMapRepository.save(dailyMap);

            LOGGER.info("New daily map chosen for {}: {}", today, randomMap.getName());
        }
    }

}