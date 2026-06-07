package com.trialmaple.service;

import com.trialmaple.config.CacheName;
import com.trialmaple.model.dto.DailyStatsDto;
import com.trialmaple.model.dto.UserStatsDto;
import com.trialmaple.model.entities.Score;
import com.trialmaple.model.entities.User;
import com.trialmaple.model.entities.dailymap.DailyMap;
import com.trialmaple.model.enums.GameMode;
import com.trialmaple.repository.DailyMapRepository;
import com.trialmaple.repository.ScoreRepository;
import com.trialmaple.service.dailymap.DailyMapService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class StatisticsService {

    private final ScoreRepository scoreRepository;
    private final DailyMapRepository dailyMapRepository;
    private final DailyMapService dailyMapService;

    // Starting date to consider for player statistics (where login feature got added)
    private static final LocalDate STATS_START_DATE = LocalDate.of(2026, 5, 4);

    public StatisticsService(ScoreRepository scoreRepository, DailyMapRepository dailyMapRepository, DailyMapService dailyMapService) {
        this.scoreRepository = scoreRepository;
        this.dailyMapRepository = dailyMapRepository;
        this.dailyMapService = dailyMapService;
    }

    /**
     * Get daily stats and daily map number
     */
    @Cacheable(value = CacheName.DAILY_STATS, key = "#gameMode")
    public DailyStatsDto getDailyStats(GameMode gameMode) {
        DailyMap currentDailyMap = dailyMapService.getCurrentDailyMap(gameMode);

        long mapNumber = dailyMapRepository.countByGameMode(currentDailyMap.getGameMode());

        List<Score> scores = scoreRepository.findByDailyMap(currentDailyMap);

        int totalWinners = scores.size();
        double averageTries = scores.stream().mapToInt(Score::getAttemptCount).average().orElse(0.0);
        // Rounded to 1 decimal
        averageTries = Math.round(averageTries * 10.0) / 10.0;

        return new DailyStatsDto(mapNumber ,totalWinners, averageTries);
    }

    /**
     * Get a user overall stats
     */
    @Cacheable(value = CacheName.USER_STATS, key = "{#user.id, #gameMode}")
    public UserStatsDto getUserStats(User user, GameMode gameMode) {
        int dailyMapsCount = dailyMapRepository.countByGameModeAndDayGreaterThanEqual(gameMode, STATS_START_DATE);
        int winsCount = scoreRepository.countByUserAndDailyMap_GameModeAndDailyMap_DayGreaterThanEqual(user, gameMode, STATS_START_DATE);
        Double avgTries = scoreRepository.getAverageAttemptCount(user, gameMode, STATS_START_DATE);

        return new UserStatsDto(
                (avgTries != null) ? avgTries : 0.0,
                winsCount,
                dailyMapsCount,
                gameMode,
                gameMode.getTmGame(),
                gameMode.getTmCategory()
        );
    }

}
