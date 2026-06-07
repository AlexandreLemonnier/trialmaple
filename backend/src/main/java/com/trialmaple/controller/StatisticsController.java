package com.trialmaple.controller;

import com.trialmaple.exception.InvalidGameModeException;
import com.trialmaple.model.dto.DailyStatsDto;
import com.trialmaple.model.dto.UserStatsDto;
import com.trialmaple.model.entities.User;
import com.trialmaple.model.enums.GameMode;
import com.trialmaple.security.annotation.CurrentUser;
import com.trialmaple.service.StatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class StatisticsController {

    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    /**
     * Get daily stats and daily map number
     */
    @GetMapping("/daily-stats")
    public DailyStatsDto getDailyStats(@RequestParam String gameMode) {
        try {
            GameMode gameModeValue = GameMode.valueOf(gameMode);
            return statisticsService.getDailyStats(gameModeValue);
        } catch (IllegalArgumentException e) {
            log.error("Invalid game mode.", e);
            throw new InvalidGameModeException(gameMode);
        }
    }

    /**
     * Get user stats
     */
    @GetMapping("/user-stats")
    public List<UserStatsDto> getUserStats(@CurrentUser User user) {
        return Arrays.stream(GameMode.values())
                .map(gameMode -> statisticsService.getUserStats(user, gameMode))
                .toList();
    }
}
