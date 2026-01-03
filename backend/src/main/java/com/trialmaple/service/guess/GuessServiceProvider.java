package com.trialmaple.service.guess;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.trialmaple.model.enums.GameMode;

@Service
public class GuessServiceProvider {
    
    private final Map<GameMode, IGuessGameModeService> services;

    public GuessServiceProvider(List<IGuessGameModeService> services) {
        this.services = services.stream().collect(Collectors.toMap(IGuessGameModeService::getGameMode, Function.identity()));
    }

    public IGuessGameModeService getGuessService(GameMode gameMode) {
        IGuessGameModeService service = services.get(gameMode);
        if (service == null) {
            throw new IllegalArgumentException("No GuessService found for game mode: " + gameMode);
        }
        return service;
    }
}
