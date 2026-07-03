package com.trialmaple.dailymap.zoom;

import com.trialmaple.core.GameMode;
import com.trialmaple.picture.ZoomPictureService;
import org.springframework.stereotype.Service;

@Service
public class ZoomTmnfTrialPickerService extends AbstractZoomPickerService {

    public ZoomTmnfTrialPickerService(ZoomPictureService zoomPictureService) {
        super(zoomPictureService);
    }

    @Override
    public GameMode getSupportedGameMode() {
        return GameMode.ZOOM_TMNF_TRIAL;
    }
}
