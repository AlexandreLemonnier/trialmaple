package com.trialmaple.statistics;

import com.trialmaple.core.config.RouteKey;
import com.trialmaple.core.exception.InvalidGameModeException;
import com.trialmaple.user.User;
import com.trialmaple.core.GameMode;
import com.trialmaple.security.annotation.CurrentUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(RouteKey.BASE_API)
@CrossOrigin(origins = "*")
public class StatisticsController {

    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    /**
     * Get daily stats and daily map number
     */
    @GetMapping(RouteKey.DAILY_STATS)
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
    @GetMapping(RouteKey.USER_STATS)
    public List<UserStatsDto> getUserStats(@CurrentUser User user) {
        return Arrays.stream(GameMode.values())
                .map(gameMode -> statisticsService.getUserStats(user, gameMode))
                .toList();
    }
}
