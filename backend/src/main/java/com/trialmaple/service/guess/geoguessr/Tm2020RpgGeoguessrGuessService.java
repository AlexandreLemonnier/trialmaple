package com.trialmaple.service.guess.geoguessr;

import com.trialmaple.model.enums.GameMode;
import com.trialmaple.service.ScoreService;
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
