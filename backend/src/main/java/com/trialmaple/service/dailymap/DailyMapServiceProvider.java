package com.trialmaple.service.dailymap;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.trialmaple.model.entities.dailymap.DailyMap;
import com.trialmaple.model.enums.GameMode;

@Service
public class DailyMapServiceProvider {
    
    private final Map<GameMode, IDailyMapPickerStrategy<? extends DailyMap>> services;

    public DailyMapServiceProvider(List<IDailyMapPickerStrategy<? extends DailyMap>> services) {
        this.services = services.stream().collect(Collectors.toMap(IDailyMapPickerStrategy::getSupportedGameMode, Function.identity()));
    }

    /**
     * Provides the correct DailyMapPickerService for the given game mode
     */
    public IDailyMapPickerStrategy<? extends DailyMap> getDailyMapService(GameMode gameMode) {
        IDailyMapPickerStrategy<? extends DailyMap> service = services.get(gameMode);
        if (service == null) {
            throw new IllegalArgumentException("No DailyMapService found for game mode: " + gameMode);
        }
        return service;
    }
}