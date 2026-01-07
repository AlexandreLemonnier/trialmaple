package com.trialmaple.service.dailymap;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.trialmaple.model.enums.GameMode;

@Service
public class DailyMapServiceProvider {
    
    private final Map<GameMode, IDailyMapPickerStrategy> services;

    public DailyMapServiceProvider(List<IDailyMapPickerStrategy> services) {
        this.services = services.stream().collect(Collectors.toMap(IDailyMapPickerStrategy::getSupportedGameMode, Function.identity()));
    }

    /**
     * Provides the correct DailyMapPickerService for the given game mode
     */
    public IDailyMapPickerStrategy getDailyMapService(GameMode gameMode) {
        IDailyMapPickerStrategy service = services.get(gameMode);
        if (service == null) {
            throw new IllegalArgumentException("No DailyMapService found for game mode: " + gameMode);
        }
        return service;
    }
}