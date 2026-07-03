package com.trialmaple.dailymap.classic;

import com.trialmaple.tmmap.TmMap;
import com.trialmaple.core.GameMode;
import com.trialmaple.tmmap.MapList;
import com.trialmaple.dailymap.DailyMapRepository;
import com.trialmaple.tmmap.TmMapRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassicTmnfRpgPickerService extends AbstractClassicPickerService {

    public ClassicTmnfRpgPickerService(TmMapRepository tmMapRepository, DailyMapRepository dailyMapRepository) {
        super(tmMapRepository, dailyMapRepository);
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