package com.trialmaple.guess.geoguessr;

import com.trialmaple.core.GameMode;
import com.trialmaple.score.ScoreService;
import org.springframework.stereotype.Service;

@Service
public class Tm2020RpgGeoguessrGuessService extends AbstractGeoguessrGuessService {

    public Tm2020RpgGeoguessrGuessService(ScoreService scoreService) {
        super(scoreService);
    }

    @Override
    public GameMode getGameMode() {
        return GameMode.GEOGUESSR_TM2020_RPG;
    }
}
