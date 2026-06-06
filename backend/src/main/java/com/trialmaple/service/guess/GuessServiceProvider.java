package com.trialmaple.service.guess;

import com.trialmaple.model.entities.dailymap.DailyMap;
import com.trialmaple.model.enums.GameMode;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class GuessServiceProvider {
    
    private final Map<GameMode, AbstractGuessGameModeService<? extends DailyMap>> services;

    public GuessServiceProvider(List<AbstractGuessGameModeService<? extends DailyMap>> services) {
        this.services = services.stream().collect(Collectors.toMap(AbstractGuessGameModeService::getGameMode, Function.identity()));
    }

    /**
     * Provides the correct GuessGameModeService for the given game mode
     */
    @SuppressWarnings("unchecked")
    public <T extends DailyMap> AbstractGuessGameModeService<T> getGuessService(GameMode gameMode) {
        AbstractGuessGameModeService<? extends DailyMap> service = services.get(gameMode);
        if (service == null) {
            throw new IllegalArgumentException("No GuessService found for game mode: " + gameMode);
        }
        return (AbstractGuessGameModeService<T>) service;
    }
}
