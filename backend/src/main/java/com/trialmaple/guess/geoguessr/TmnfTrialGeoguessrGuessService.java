package com.trialmaple.guess.geoguessr;

import com.trialmaple.core.GameMode;
import com.trialmaple.score.ScoreService;
import org.springframework.stereotype.Service;

@Service
public class TmnfTrialGeoguessrGuessService extends AbstractGeoguessrGuessService {

    public TmnfTrialGeoguessrGuessService(ScoreService scoreService) {
        super(scoreService);
    }

    @Override
    public GameMode getGameMode() {
        return GameMode.GEOGUESSR_TMNF_TRIAL;
    }
}
