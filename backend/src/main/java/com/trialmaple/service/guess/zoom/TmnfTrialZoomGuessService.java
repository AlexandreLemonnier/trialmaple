package com.trialmaple.service.guess.zoom;

import com.trialmaple.model.enums.GameMode;
import com.trialmaple.repository.ScoreRepository;
import org.springframework.stereotype.Service;

@Service
public class TmnfTrialZoomGuessService extends AbstractZoomGuessService {

    public TmnfTrialZoomGuessService(ScoreRepository scoreRepository) {
        super(scoreRepository);
    }

    @Override
    public GameMode getGameMode() {
        return GameMode.ZOOM_TMNF_TRIAL;
    }
}
