package com.trialmaple.dailymap.geoguessr;

import com.trialmaple.picture.DailyPictures;
import com.trialmaple.tmmap.TmMap;
import com.trialmaple.core.GameMode;
import com.trialmaple.dailymap.IDailyMapPickerStrategy;
import com.trialmaple.picture.GeoguessrPictureService;
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
