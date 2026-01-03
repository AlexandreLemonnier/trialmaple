package com.trialmaple.service.maps;

import java.util.List;

import org.springframework.stereotype.Service;

import com.trialmaple.model.entities.TmMap;
import com.trialmaple.model.enums.GameMode;
import com.trialmaple.service.dailymap.DailyMapServiceProvider;
import com.trialmaple.service.dailymap.IDailyMapPickerStrategy;
import com.trialmaple.service.maps.update.IMapUpdateStrategy;

@Service
public class TmMapService {
    private final List<IMapUpdateStrategy> updateStrategies;
    private final DailyMapServiceProvider provider;

    public TmMapService(List<IMapUpdateStrategy> updateStrategies, DailyMapServiceProvider provider) {
        this.updateStrategies = updateStrategies;
        this.provider = provider;
    }

    public List<String> getAllMapNames(GameMode gameMode) {
        IDailyMapPickerStrategy dailyMapService = provider.getDailyMapService(gameMode);
        List<TmMap> maps = dailyMapService.getMapPool();
        return maps.stream().map(tmMap -> tmMap.getName()).toList();
    }

    public void fetchAndUpdateMaps() {
        updateStrategies.forEach(IMapUpdateStrategy::fetchAndUpdate);
    }

}
