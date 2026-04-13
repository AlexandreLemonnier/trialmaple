package com.trialmaple.service.dailymap.classic;

import com.trialmaple.model.entities.TmMap;
import com.trialmaple.model.enums.GameMode;
import com.trialmaple.model.enums.MapList;
import com.trialmaple.repository.DailyMapRepository;
import com.trialmaple.repository.TmMapRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassicTm2TrialPickerService extends AbstractClassicPickerService {

    public ClassicTm2TrialPickerService(TmMapRepository tmMapRepository, DailyMapRepository dailyMapRepository) {
        super(tmMapRepository, dailyMapRepository);
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
