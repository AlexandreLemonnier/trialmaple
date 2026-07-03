package com.trialmaple.statistics;

public record DailyStatsDto(
        long mapNumber,
        int winnersCount,
        double averageTries) {
}
