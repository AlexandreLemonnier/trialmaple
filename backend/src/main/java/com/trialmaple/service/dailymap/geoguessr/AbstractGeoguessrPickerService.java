package com.trialmaple.service.dailymap.geoguessr;

import com.trialmaple.model.entities.DailyMap;
import com.trialmaple.model.entities.DailyPictures;
import com.trialmaple.model.entities.TmMap;
import com.trialmaple.service.dailymap.IDailyMapPickerStrategy;
import com.trialmaple.service.maps.PicturesService;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Slf4j
public abstract class AbstractGeoguessrPickerService implements IDailyMapPickerStrategy {

    PicturesService picturesService;

    private final Random random = new Random();

    protected AbstractGeoguessrPickerService(PicturesService picturesService) {
        this.picturesService = picturesService;
    }

    /**
     * Name of the folder under src/main/resources containing pictures corresponding to the game mode
     */
    abstract String getPicturesFolder();

    @Override
    public DailyMap pickDailyMap(LocalDate day) {
        DailyPictures dailyPictures = picturesService.getRandomMap(getPicturesFolder());
        if (dailyPictures == null) {
            return null;
        }
        return new DailyMap(dailyPictures, day, getSupportedGameMode());
    }

    @Override
    public List<TmMap> getMapPool() {
        return List.of();
    }

}
