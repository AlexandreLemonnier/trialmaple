package com.trialmaple.service.dailymap;

import java.util.List;

import org.springframework.stereotype.Service;

import com.trialmaple.model.entities.TmMap;
import com.trialmaple.model.enums.GameMode;
import com.trialmaple.model.enums.MapList;
import com.trialmaple.repository.TmMapRepository;

@Service
public class ClassicTmnfRpgPickerService implements IDailyMapPickerStrategy {

    private final TmMapRepository tmMapRepository;

    public ClassicTmnfRpgPickerService(TmMapRepository tmMapRepository) {
        this.tmMapRepository = tmMapRepository;
    }

    @Override
    public GameMode getSupportedGameMode() {
        return GameMode.CLASSIC_TMNF_RPG;
    }

    @Override
    public List<TmMap> getMapPool() {
        return tmMapRepository.findByWrTimeIsNotNullAndActiveTrueAndMapList(MapList.TMNF_RPG_CLASSIC);
    }
    
}