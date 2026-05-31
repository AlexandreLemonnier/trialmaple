package com.trialmaple.service.dailymap.blur;

import com.trialmaple.model.enums.GameMode;
import com.trialmaple.service.maps.BlurPictureService;
import org.springframework.stereotype.Service;

@Service
public class BlurTmnfTrialPickerService extends AbstractBlurPickerService {

    public BlurTmnfTrialPickerService(BlurPictureService blurPictureService) {
        super(blurPictureService);
    }

    @Override
    public GameMode getSupportedGameMode() {
        return GameMode.BLUR_TMNF_TRIAL;
    }
}
