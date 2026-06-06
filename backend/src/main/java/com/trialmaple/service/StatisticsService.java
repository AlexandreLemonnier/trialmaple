package com.trialmaple.service;

import com.trialmaple.config.CacheName;
import com.trialmaple.model.dto.DailyStatsDto;
import com.trialmaple.model.entities.dailymap.DailyMap;
import com.trialmaple.model.entities.Score;
import com.trialmaple.model.enums.GameMode;
import com.trialmaple.repository.DailyMapRepository;
import com.trialmaple.repository.ScoreRepository;
import com.trialmaple.service.dailymap.DailyMapService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticsService {

    private final ScoreRepository scoreRepository;
    private final DailyMapRepository dailyMapRepository;
    private final DailyMapService dailyMapService;

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

}
