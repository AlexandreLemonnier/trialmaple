package com.trialmaple.guess.blur;

import com.trialmaple.core.GameMode;
import com.trialmaple.score.ScoreService;
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
