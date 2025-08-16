package com.trialmaple.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trialmaple.model.dto.DailyStatsDto;
import com.trialmaple.service.StatisticsService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class StatisticsController {

    private StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/daily-stats")
    public DailyStatsDto getDaiyStats() {
        return statisticsService.getDailyStats();
    }
}
