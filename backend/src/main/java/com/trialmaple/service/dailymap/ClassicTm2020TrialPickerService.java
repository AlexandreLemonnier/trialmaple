package com.trialmaple.service.dailymap;

import java.util.List;

import org.springframework.stereotype.Service;

import com.trialmaple.model.entities.TmMap;
import com.trialmaple.model.enums.GameMode;
import com.trialmaple.model.enums.MapList;
import com.trialmaple.repository.TmMapRepository;

@Service
public class ClassicTm2020TrialPickerService implements IDailyMapPickerStrategy {
    private final TmMapRepository tmMapRepository;

    public ClassicTm2020TrialPickerService(TmMapRepository tmMapRepository) {
        this.tmMapRepository = tmMapRepository;
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