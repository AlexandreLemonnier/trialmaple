package com.trialmaple.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trialmaple.exception.InvalidGameModeException;
import com.trialmaple.model.dto.DailyStatsDto;
import com.trialmaple.model.enums.GameMode;
import com.trialmaple.service.StatisticsService;
import com.trialmaple.utils.LoggerWrapper;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class StatisticsController {

    private static final LoggerWrapper LOGGER = new LoggerWrapper(StatisticsController.class);

    private StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/daily-stats")
    public DailyStatsDto getDaiyStats(@RequestParam String gameMode) {
        try {
            GameMode gameModeValue = GameMode.valueOf(gameMode);
            return statisticsService.getDailyStats(gameModeValue);
        } catch (IllegalArgumentException e) {
            LOGGER.error("Invalid game mode.", e);
            throw new InvalidGameModeException(gameMode);
        }
    }
}
