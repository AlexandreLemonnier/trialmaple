package com.trialmaple.dailymap.geoguessr;

import com.trialmaple.core.GameMode;
import com.trialmaple.picture.GeoguessrPictureService;
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
