package com.trialmaple.model.entities.dailymap;

import com.trialmaple.model.entities.TmMap;
import com.trialmaple.model.enums.GameMode;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@DiscriminatorValue("CLASSIC")
@Getter
@NoArgsConstructor
public class ClassicDailyMap extends DailyMap {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tm_map_id")
    private TmMap map;

    public ClassicDailyMap(TmMap map, LocalDate day, GameMode gameMode) {
        super(day, gameMode);
        this.map = map;
    }

    @Override
    public String getMapName() {
        return map.getName();
    }
}
