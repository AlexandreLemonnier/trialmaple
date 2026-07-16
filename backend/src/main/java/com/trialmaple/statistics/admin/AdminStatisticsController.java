package com.trialmaple.statistics.admin;

import com.trialmaple.core.GameMode;
import com.trialmaple.core.config.RouteKey;
import com.trialmaple.statistics.StatisticsService;
import com.trialmaple.statistics.UserStatsDto;
import com.trialmaple.user.User;
import com.trialmaple.user.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(RouteKey.ADMIN_PREFIX + RouteKey.USER_STATS)
@CrossOrigin(origins = "*")
public class AdminStatisticsController {

    private final UserService userService;
    private final StatisticsService statisticsService;

    public AdminStatisticsController(UserService userService, StatisticsService statisticsService) {
        this.userService = userService;
        this.statisticsService = statisticsService;
    }

    /**
     * Get user stats
     */
    @GetMapping("/{discordId}")
    public List<UserStatsDto> getUserStats(@PathVariable String discordId) {
        User user = userService.findUserByDiscordId(discordId);
        return Arrays.stream(GameMode.values())
                .map(gameMode -> statisticsService.getUserStats(user, gameMode))
                .toList();
    }
}
