package com.trialmaple.service.guess.blur;

import com.trialmaple.model.enums.GameMode;
import com.trialmaple.repository.ScoreRepository;
import org.springframework.stereotype.Service;

@Service
public class TmnfTrialBlurGuessService extends AbstractBlurGuessService {

    public TmnfTrialBlurGuessService(ScoreRepository scoreRepository) {
        super(scoreRepository);
    }
    @Override
    public GameMode getGameMode() {
        return GameMode.BLUR_TMNF_TRIAL;
    }
}
