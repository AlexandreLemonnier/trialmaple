package com.trialmaple.dailymap.blur;

import com.trialmaple.core.GameMode;
import com.trialmaple.picture.BlurPictureService;
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
