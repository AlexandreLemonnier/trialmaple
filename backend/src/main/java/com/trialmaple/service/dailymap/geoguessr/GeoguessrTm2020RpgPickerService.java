package com.trialmaple.service.dailymap.geoguessr;

import com.trialmaple.model.enums.GameMode;
import com.trialmaple.service.maps.PicturesService;
import org.springframework.stereotype.Service;

@Service
public class GeoguessrTm2020RpgPickerService extends AbstractGeoguessrPickerService {

    public GeoguessrTm2020RpgPickerService(PicturesService picturesService) {
        super(picturesService);
    }

    @Override
    public GameMode getSupportedGameMode() {
        return GameMode.GEOGUESSR_TM2020_TRIAL;
    }

    @Override
    String getPicturesFolder() {
        return "TM2020_RPG";
    }
}
