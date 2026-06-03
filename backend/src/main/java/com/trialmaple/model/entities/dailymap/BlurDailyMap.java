package com.trialmaple.model.entities.dailymap;

import com.trialmaple.model.entities.DailyPictures;
import com.trialmaple.model.enums.GameMode;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@DiscriminatorValue("BLUR")
@Getter
@NoArgsConstructor
public class BlurDailyMap extends DailyMap {

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "daily_pictures_id")
    private DailyPictures dailyPictures;

    public BlurDailyMap(DailyPictures dailyPictures, LocalDate day, GameMode gameMode) {
        super(day, gameMode);
        this.dailyPictures = dailyPictures;
    }

    @Override
    public String getMapName() {
        return dailyPictures.getMapName();
    }
}