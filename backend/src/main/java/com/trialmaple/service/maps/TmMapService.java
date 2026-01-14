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

    /**
     * Get the map pool names for the given game mode
     */
    public List<String> getAllMapNames(GameMode gameMode) {
        IDailyMapPickerStrategy dailyMapService = provider.getDailyMapService(gameMode);
        List<TmMap> maps = dailyMapService.getMapPool();
        return maps.stream().map(TmMap::getName).toList();
    }

    /**
     * Fetch maps data from an external API and updated them if needed
     */
    public void fetchAndUpdateMaps() {
        updateStrategies.forEach(IMapUpdateStrategy::fetchAndUpdate);
    }

}
