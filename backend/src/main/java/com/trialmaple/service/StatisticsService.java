package com.trialmaple.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.trialmaple.model.dto.DailyStatsDto;
import com.trialmaple.model.entities.DailyMap;
import com.trialmaple.model.entities.Score;
import com.trialmaple.repository.ScoreRepository;
import com.trialmaple.service.dailymap.DailyMapService;

@Service
public class StatisticsService {

    private final ScoreRepository scoreRepository;
    private final DailyMapService dailyMapService;

    public StatisticsService(ScoreRepository scoreRepository, DailyMapService dailyMapService) {
        this.scoreRepository = scoreRepository;
        this.dailyMapService = dailyMapService;
    }

    public DailyStatsDto getDailyStats() {
        DailyMap currentDailyMap = dailyMapService.getCurrentDailyMap();

        List<Score> scores = scoreRepository.findByDailyMap(currentDailyMap);

        int totalWinners = scores.size();
        double averageTries = scores.stream().mapToInt(Score::getAttemptCount).average().orElse(0.0);
        // Rounded to 1 decimal
        averageTries = Math.round(averageTries * 10.0) / 10.0;

        return new DailyStatsDto(currentDailyMap.getId() ,totalWinners, averageTries);
    }

}
