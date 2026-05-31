package com.trialmaple.service.dailymap.geoguessr;

import com.trialmaple.model.entities.DailyPictures;
import com.trialmaple.model.entities.GeoguessrDailyMap;
import com.trialmaple.model.entities.TmMap;
import com.trialmaple.model.enums.GameMode;
import com.trialmaple.service.dailymap.IDailyMapPickerStrategy;
import com.trialmaple.service.picture.GeoguessrPictureService;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.List;

@Slf4j
public abstract class AbstractGeoguessrPickerService implements IDailyMapPickerStrategy<GeoguessrDailyMap> {

    GeoguessrPictureService geoguessrPictureService;

    protected AbstractGeoguessrPickerService(GeoguessrPictureService geoguessrPictureService) {
        this.geoguessrPictureService = geoguessrPictureService;
    }

    @Override
    public GeoguessrDailyMap pickDailyMap(LocalDate day) {
        GameMode gameMode = getSupportedGameMode();
        String picturesFolder = getSupportedGameMode().getPicturesFolderName();
        DailyPictures dailyPictures = geoguessrPictureService.getRandomMap(picturesFolder, gameMode);
        if (dailyPictures == null) {
            return null;
        }
        return new GeoguessrDailyMap(dailyPictures, day, getSupportedGameMode());
    }

    @Override
    public List<TmMap> getMapPool() {
        return List.of();
    }

}
