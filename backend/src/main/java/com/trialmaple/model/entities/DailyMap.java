package com.trialmaple.model.entities;

import java.time.LocalDate;
import java.util.UUID;

import com.trialmaple.model.enums.GameMode;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

    @Column(name = "uuid", unique = true, nullable = false, updatable = false)
    private final UUID uuid = UUID.randomUUID();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tm_map_id", nullable = false)
    private TmMap map;

    @Column(name = "day", columnDefinition = "DATE")
    private LocalDate day;

    @Enumerated(EnumType.STRING)
    @Column(name = "game_mode", columnDefinition = "VARCHAR(50)")
    private GameMode gameMode;

    protected DailyMap() {
    }

    public DailyMap(TmMap map, LocalDate day, GameMode gameMode) {
        this.map = map;
        this.day = day;
        this.gameMode = gameMode;
    }

    public Long getId() {
        return id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public TmMap getMap() {
        return map;
    }

    public LocalDate getDay() {
        return day;
    }

    public GameMode getGameMode() {
        return gameMode;
    }
}
