package com.trialmaple.model.dto;

public record DailyStatsDto(
        long mapNumber,
        int winnersCount,
        double averageTries) {
}
