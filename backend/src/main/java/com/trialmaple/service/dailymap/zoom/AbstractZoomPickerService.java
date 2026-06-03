package com.trialmaple.service.dailymap.zoom;

import com.trialmaple.model.entities.DailyPictures;
import com.trialmaple.model.entities.TmMap;
import com.trialmaple.model.entities.dailymap.ZoomDailyMap;
import com.trialmaple.model.enums.GameMode;
import com.trialmaple.service.dailymap.IDailyMapPickerStrategy;
import com.trialmaple.service.picture.ZoomPictureService;
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
