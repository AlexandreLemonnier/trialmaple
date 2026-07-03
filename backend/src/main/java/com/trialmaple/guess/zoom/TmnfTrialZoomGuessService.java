package com.trialmaple.guess.zoom;

import com.trialmaple.core.GameMode;
import com.trialmaple.score.ScoreService;
import org.springframework.stereotype.Service;

@Service
public class TmnfTrialZoomGuessService extends AbstractZoomGuessService {

    public TmnfTrialZoomGuessService(ScoreService scoreService) {
        super(scoreService);
    }

    @Override
    public GameMode getGameMode() {
        return GameMode.ZOOM_TMNF_TRIAL;
    }
}
