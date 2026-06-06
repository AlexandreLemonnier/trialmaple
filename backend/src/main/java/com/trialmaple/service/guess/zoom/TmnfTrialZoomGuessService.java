package com.trialmaple.service.guess.zoom;

import com.trialmaple.model.enums.GameMode;
import com.trialmaple.service.ScoreService;
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
