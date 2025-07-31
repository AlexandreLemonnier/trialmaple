package com.trialmaple.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.trialmaple.model.entities.TrialMap;
import com.trialmaple.repository.TrialMapRepository;

@Service
public class DailyMapService {
    private final TrialMapRepository mapRepo;

    private TrialMap currentMap;
    private LocalDate currentDate;

    public DailyMapService(TrialMapRepository mapRepo) {
        this.mapRepo = mapRepo;
        // refreshIfNeeded();
    }

    public TrialMap getTodayMap() {
        refreshIfNeeded();
        return currentMap;
    }

    private void refreshIfNeeded() {
        if (currentMap == null || !LocalDate.now().equals(currentDate)) {
            List<TrialMap> all = mapRepo.findAll();
            int index = LocalDate.now().getDayOfYear() % all.size();
            currentMap = all.get(index);
            currentDate = LocalDate.now();
        }
    }
}