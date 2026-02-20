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

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class DailyMap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid", unique = true, nullable = false, updatable = false)
    private final UUID uuid = UUID.randomUUID();

    @JoinColumn(name = "tm_map_id", nullable = false)
    private TmMap map;

    private LocalDate day;

    @Enumerated(EnumType.STRING)
    private GameMode gameMode;

    public DailyMap(TmMap map, LocalDate day, GameMode gameMode) {
        this.map = map;
        this.day = day;
        this.gameMode = gameMode;
    }
}
