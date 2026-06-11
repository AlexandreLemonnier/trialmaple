package com.trialmaple.service.guess.geoguessr;

import com.trialmaple.model.enums.GameMode;
import com.trialmaple.service.ScoreService;
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
