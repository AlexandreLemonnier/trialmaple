package com.trialmaple.service.dailymap;

import java.util.List;

import org.springframework.stereotype.Service;

import com.trialmaple.model.entities.TmMap;
import com.trialmaple.model.enums.GameMode;
import com.trialmaple.model.enums.MapList;
import com.trialmaple.repository.TmMapRepository;

@Service
public class ClassicTm2RpgPickerService implements IDailyMapPickerStrategy {
    private final TmMapRepository tmMapRepository;

    public ClassicTm2RpgPickerService(TmMapRepository tmMapRepository) {
        this.tmMapRepository = tmMapRepository;
    }

    @Override
    public GameMode getSupportedGameMode() {
        return GameMode.CLASSIC_TM2_RPG;
    }

    @Override
    public List<TmMap> getMapPool() {
        return tmMapRepository.findByWrTimeIsNotNullAndActiveTrueAndMapList(MapList.TM2_RPG_PVM);
    }
}