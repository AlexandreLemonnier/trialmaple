package com.trialmaple.dailymap.geoguessr;

import com.trialmaple.dailymap.DailyMap;
import com.trialmaple.picture.DailyPictures;
import com.trialmaple.core.GameMode;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@DiscriminatorValue("GEOGUESSR")
@Getter
@NoArgsConstructor
public class GeoguessrDailyMap extends DailyMap {

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "daily_pictures_id")
    private DailyPictures dailyPictures;

    public GeoguessrDailyMap(DailyPictures dailyPictures, LocalDate day, GameMode gameMode) {
        super(day, gameMode);
        this.dailyPictures = dailyPictures;
    }

    @Override
    public String getMapName() {
        return dailyPictures.getMapName();
    }
}