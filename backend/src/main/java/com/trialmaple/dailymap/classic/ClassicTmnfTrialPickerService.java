package com.trialmaple.dailymap.classic;

import com.trialmaple.core.GameMode;
import com.trialmaple.dailymap.DailyMapRepository;
import com.trialmaple.tmmap.TmMap;
import com.trialmaple.tmmap.TmMapRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassicTmnfTrialPickerService extends AbstractClassicPickerService {

    public ClassicTmnfTrialPickerService(TmMapRepository tmMapRepository, DailyMapRepository dailyMapRepository) {
        super(tmMapRepository, dailyMapRepository);
    }

    @Override
    public GameMode getSupportedGameMode() {
        return GameMode.CLASSIC_TMNF_TRIAL;
    }

    @Override
    public List<TmMap> getMapPool() {
        return tmMapRepository.findByWrTimeIsNotNullAndActiveTrueAndMapList(getSupportedGameMode().getMapList());
    }
    
}
