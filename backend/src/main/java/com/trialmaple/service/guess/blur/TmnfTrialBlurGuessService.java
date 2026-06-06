package com.trialmaple.service.guess.blur;

import com.trialmaple.model.enums.GameMode;
import com.trialmaple.service.ScoreService;
import org.springframework.stereotype.Service;

@Service
public class TmnfTrialBlurGuessService extends AbstractBlurGuessService {

    public TmnfTrialBlurGuessService(ScoreService scoreService) {
        super(scoreService);
    }

    @Override
    public GameMode getGameMode() {
        return GameMode.BLUR_TMNF_TRIAL;
    }
}
