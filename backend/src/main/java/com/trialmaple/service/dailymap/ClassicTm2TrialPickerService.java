package com.trialmaple.service.dailymap;

import java.util.List;

import com.trialmaple.model.entities.TmMap;
import com.trialmaple.model.enums.GameMode;
import com.trialmaple.model.enums.MapList;
import com.trialmaple.repository.TmMapRepository;

// @Service
public class ClassicTm2TrialPickerService implements IDailyMapPickerStrategy {
    private final TmMapRepository tmMapRepository;

    public ClassicTm2TrialPickerService(TmMapRepository tmMapRepository) {
        this.tmMapRepository = tmMapRepository;
    }

    @Override
    public GameMode getSupportedGameMode() {
        return GameMode.CLASSIC_TM2_TRIAL;
    }

    @Override
    public List<TmMap> getMapPool() {
        return tmMapRepository.findByWrTimeIsNotNullAndActiveTrueAndMapList(MapList.TM2_TRIAL);
    }
}
