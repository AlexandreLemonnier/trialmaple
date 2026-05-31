package com.trialmaple.service.dailymap.geoguessr;

import com.trialmaple.model.enums.GameMode;
import com.trialmaple.service.maps.GeoguessrPictureService;
import org.springframework.stereotype.Service;

@Service
public class GeoguessrTm2020RpgPickerService extends AbstractGeoguessrPickerService {

    public GeoguessrTm2020RpgPickerService(GeoguessrPictureService geoguessrPictureService) {
        super(geoguessrPictureService);
    }

    @Override
    public GameMode getSupportedGameMode() {
        return GameMode.GEOGUESSR_TM2020_RPG;
    }
}
