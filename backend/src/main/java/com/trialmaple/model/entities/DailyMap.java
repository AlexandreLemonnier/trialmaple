package com.trialmaple.model.entities;

import com.trialmaple.model.enums.GameMode;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class DailyMap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid", unique = true, nullable = false, updatable = false)
    private final UUID uuid = UUID.randomUUID();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tm_map_id")
    private TmMap map;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "daily_pictures_id")
    private DailyPictures dailyPictures;

    private LocalDate day;

    @Enumerated(EnumType.STRING)
    private GameMode gameMode;

    public DailyMap(TmMap map, LocalDate day, GameMode gameMode) {
        this.map = map;
        this.day = day;
        this.gameMode = gameMode;
    }

    public DailyMap(DailyPictures dailyPictures, LocalDate day, GameMode gameMode) {
        this.dailyPictures = dailyPictures;
        this.day = day;
        this.gameMode = gameMode;
    }
}
