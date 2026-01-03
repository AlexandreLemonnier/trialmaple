package com.trialmaple.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trialmaple.exception.InvalidGameModeException;
import com.trialmaple.exception.InvalidMapNameException;
import com.trialmaple.exception.NoDailyMapFoundException;
import com.trialmaple.model.dto.GuessDto;
import com.trialmaple.model.dto.GuessRequestDto;
import com.trialmaple.model.entities.DailyMap;
import com.trialmaple.model.enums.GameMode;
import com.trialmaple.service.GuessService;
import com.trialmaple.service.dailymap.DailyMapService;
import com.trialmaple.utils.LoggerWrapper;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class GuessController {

    private static final LoggerWrapper LOGGER = new LoggerWrapper(GuessController.class);

    private final DailyMapService dailyMapService;
    private final GuessService guessService;

    public GuessController(GuessService guessService, DailyMapService dailyMapService) {
        this.guessService = guessService;
        this.dailyMapService = dailyMapService;
    }

    @PostMapping("/guess")
    public GuessDto guess(@RequestBody GuessRequestDto request, @RequestParam String gameMode)
            throws NoDailyMapFoundException, InvalidMapNameException {
        try {
            GameMode gameModeValue = GameMode.valueOf(gameMode);
            DailyMap dailyMap = dailyMapService.getCurrentDailyMap(gameModeValue);
            return guessService.checkGuess(dailyMap, request);
        } catch (IllegalArgumentException e) {
            LOGGER.error("Invalid game mode.", e);
            throw new InvalidGameModeException(gameMode);
        }
    }

    @GetMapping("/guess/daily-map")
    public String getDailyMapUuid(@RequestParam String gameMode) throws NoDailyMapFoundException {
        try {
            GameMode gameModeValue = GameMode.valueOf(gameMode);
            DailyMap dailyMap = dailyMapService.getCurrentDailyMap(gameModeValue);
            return dailyMap.getUuid().toString();
        } catch (IllegalArgumentException e) {
            LOGGER.error("Invalid game mode.", e);
            throw new InvalidGameModeException(gameMode);
        }
    }
}