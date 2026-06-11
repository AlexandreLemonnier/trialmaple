package com.trialmaple.service.dailymap.geoguessr;

import com.trialmaple.model.enums.GameMode;
import com.trialmaple.service.picture.GeoguessrPictureService;
import org.springframework.stereotype.Service;

@Service
public class GeoguessrTmnfTrialPickerService extends AbstractGeoguessrPickerService {

    public GeoguessrTmnfTrialPickerService(GeoguessrPictureService geoguessrPictureService) {
        super(geoguessrPictureService);
    }

    @Override
    public GameMode getSupportedGameMode() {
        return GameMode.GEOGUESSR_TMNF_TRIAL;
    }
}
