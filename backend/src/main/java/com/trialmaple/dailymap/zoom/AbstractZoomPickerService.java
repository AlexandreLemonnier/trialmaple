package com.trialmaple.dailymap.zoom;

import com.trialmaple.picture.DailyPictures;
import com.trialmaple.tmmap.TmMap;
import com.trialmaple.core.GameMode;
import com.trialmaple.dailymap.IDailyMapPickerStrategy;
import com.trialmaple.picture.ZoomPictureService;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.List;

@Slf4j
public abstract class AbstractZoomPickerService implements IDailyMapPickerStrategy<ZoomDailyMap> {

    ZoomPictureService zoomPictureService;

    protected AbstractZoomPickerService(ZoomPictureService zoomPictureService) {
        this.zoomPictureService = zoomPictureService;
    }

    @Override
    public ZoomDailyMap pickDailyMap(LocalDate day) {
        GameMode gameMode = getSupportedGameMode();
        String picturesFolder = getSupportedGameMode().getPicturesFolderName();
        DailyPictures dailyPictures = zoomPictureService.getRandomMap(picturesFolder, gameMode);
        if (dailyPictures == null) {
            return null;
        }
        return new ZoomDailyMap(dailyPictures, day, getSupportedGameMode());
    }

    @Override
    public List<TmMap> getMapPool() {
        return List.of();
    }

}
