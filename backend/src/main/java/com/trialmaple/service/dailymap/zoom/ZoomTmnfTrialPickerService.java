package com.trialmaple.service.dailymap.zoom;

import com.trialmaple.model.enums.GameMode;
import com.trialmaple.service.picture.ZoomPictureService;
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
