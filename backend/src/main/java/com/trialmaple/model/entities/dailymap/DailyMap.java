package com.trialmaple.model.entities.dailymap;

import com.trialmaple.model.enums.GameMode;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@DiscriminatorColumn(name = "mode")
@Getter
@NoArgsConstructor
public abstract class DailyMap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid", unique = true, nullable = false, updatable = false)
    private final UUID uuid = UUID.randomUUID();

    private LocalDate day;

    @Enumerated(EnumType.STRING)
    private GameMode gameMode;

    protected DailyMap(LocalDate day, GameMode gameMode) {
        this.day = day;
        this.gameMode = gameMode;
    }

    public abstract String getMapName();
}
