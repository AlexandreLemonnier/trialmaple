package com.trialmaple.service.dailymap.classic;

import com.trialmaple.model.entities.TmMap;
import com.trialmaple.model.enums.GameMode;
import com.trialmaple.model.enums.MapList;
import com.trialmaple.repository.DailyMapRepository;
import com.trialmaple.repository.TmMapRepository;
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
        return tmMapRepository.findByWrTimeIsNotNullAndActiveTrueAndMapList(MapList.TMNF_TRIAL_HARDEST);
    }
    
}
