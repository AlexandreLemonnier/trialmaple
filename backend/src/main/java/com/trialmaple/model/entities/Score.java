package com.trialmaple.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "score")
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT")
    private Long id;

    @Column(name = "attempt_count", columnDefinition = "INT")
    private int attemptCount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "daily_map_id", nullable = false)
    private DailyMap dailyMap;

    @Column(name = "player", columnDefinition = "VARCHAR(255)")
    private String player;

    protected Score() {
    }

    public Score(int attemptCount, DailyMap dailyMap) {
        this.attemptCount = attemptCount;
        this.dailyMap = dailyMap;
        this.player = "Unknown";
    }

    public int getAttemptCount() {
        return attemptCount;
    }

    public DailyMap getDailyMap() {
        return dailyMap;
    }

    public String getPlayer() {
        return player;
    }
}