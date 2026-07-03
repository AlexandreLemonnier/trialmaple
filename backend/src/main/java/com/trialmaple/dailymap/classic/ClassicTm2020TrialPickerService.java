package com.trialmaple.dailymap.classic;

import com.trialmaple.tmmap.TmMap;
import com.trialmaple.core.GameMode;
import com.trialmaple.tmmap.MapList;
import com.trialmaple.dailymap.DailyMapRepository;
import com.trialmaple.tmmap.TmMapRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassicTm2020TrialPickerService extends AbstractClassicPickerService {

    public ClassicTm2020TrialPickerService(TmMapRepository tmMapRepository, DailyMapRepository dailyMapRepository) {
        super(tmMapRepository, dailyMapRepository);
    }

    @Override
    public GameMode getSupportedGameMode() {
        return GameMode.CLASSIC_TM2020_TRIAL;
    }

    @Override
    public List<TmMap> getMapPool() {
        return tmMapRepository.findByWrTimeIsNotNullAndActiveTrueAndMapList(MapList.TM2020_TRIAL_CLASSIC);
    }
}