package com.trialmaple.model.dto;

public class DailyStatsDto {
    private int nbWinners;
    private double averageTries;

    public DailyStatsDto(int nbWinners, double averageTries) {
        this.nbWinners = nbWinners;
        this.averageTries = averageTries;
    }

    public int getNbWinners() {
        return nbWinners;
    }

    public double getAverageTries() {
        return averageTries;
    }
}
