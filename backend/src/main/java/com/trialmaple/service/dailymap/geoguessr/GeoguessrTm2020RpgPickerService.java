package com.trialmaple.service.dailymap.geoguessr;

import com.trialmaple.model.enums.GameMode;
import com.trialmaple.service.maps.PictureService;
import org.springframework.stereotype.Service;

@Service
public class GeoguessrTm2020RpgPickerService extends AbstractGeoguessrPickerService {

    public GeoguessrTm2020RpgPickerService(PictureService pictureService) {
        super(pictureService);
    }

    @Override
    public GameMode getSupportedGameMode() {
        return GameMode.GEOGUESSR_TM2020_RPG;
    }
}
