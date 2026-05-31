package com.trialmaple.service.dailymap.blur;

import com.trialmaple.model.entities.*;
import com.trialmaple.model.enums.GameMode;
import com.trialmaple.service.dailymap.IDailyMapPickerStrategy;
import com.trialmaple.service.maps.BlurPictureService;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.List;

@Slf4j
public abstract class AbstractBlurPickerService implements IDailyMapPickerStrategy<BlurDailyMap> {

    BlurPictureService blurPictureService;

    protected AbstractBlurPickerService(BlurPictureService blurPictureService) {
        this.blurPictureService = blurPictureService;
    }

    @Override
    public BlurDailyMap pickDailyMap(LocalDate day) {
        GameMode gameMode = getSupportedGameMode();
        String picturesFolder = getSupportedGameMode().getPicturesFolderName();
        DailyPictures dailyPictures = blurPictureService.getRandomMap(picturesFolder, gameMode);
        if (dailyPictures == null) {
            return null;
        }
        return new BlurDailyMap(dailyPictures, day, getSupportedGameMode());
    }

    @Override
    public List<TmMap> getMapPool() {
        return List.of();
    }

}
