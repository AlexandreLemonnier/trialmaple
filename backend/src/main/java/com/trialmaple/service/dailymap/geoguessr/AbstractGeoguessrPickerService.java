package com.trialmaple.service.dailymap.geoguessr;

import com.trialmaple.model.entities.DailyMap;
import com.trialmaple.model.entities.DailyPictures;
import com.trialmaple.model.entities.TmMap;
import com.trialmaple.service.dailymap.IDailyMapPickerStrategy;
import com.trialmaple.service.maps.PictureService;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.List;

@Slf4j
public abstract class AbstractGeoguessrPickerService implements IDailyMapPickerStrategy {

    PictureService pictureService;

    protected AbstractGeoguessrPickerService(PictureService pictureService) {
        this.pictureService = pictureService;
    }

    @Override
    public DailyMap pickDailyMap(LocalDate day) {
        String picturesFolder = getSupportedGameMode().getPicturesFolderName();
        DailyPictures dailyPictures = pictureService.getRandomMap(picturesFolder);
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
