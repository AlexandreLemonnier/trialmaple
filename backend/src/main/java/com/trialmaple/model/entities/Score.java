package com.trialmaple.model.entities;

import com.trialmaple.model.entities.dailymap.DailyMap;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int attemptCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "daily_map_id", nullable = false)
    private DailyMap dailyMap;

    public Score(int attemptCount, DailyMap dailyMap) {
        this.attemptCount = attemptCount;
        this.dailyMap = dailyMap;
    }
}