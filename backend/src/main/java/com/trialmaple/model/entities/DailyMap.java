package com.trialmaple.model.entities;

import java.time.LocalDate;

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
@Table(name = "daily_map")
public class DailyMap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "trial_map_id", nullable = false)
    private TrialMap map;

    @Column(name = "day", nullable = false)
    private LocalDate day;

    protected DailyMap() {
    }

    public DailyMap(TrialMap map, LocalDate day) {
        this.map = map;
        this.day = day;
    }

    public TrialMap getMap() {
        return map;
    }

    public LocalDate getDay() {
        return day;
    }
}
