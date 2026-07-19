package com.trialmaple.tmmap;

import com.trialmaple.core.GameMode;
import com.trialmaple.dailymap.DailyMap;
import com.trialmaple.dailymap.DailyMapServiceProvider;
import com.trialmaple.dailymap.IDailyMapPickerStrategy;
import com.trialmaple.tmmap.update.IMapUpdateStrategy;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class TmMapService {
    private final List<IMapUpdateStrategy> updateStrategies;
    private final DailyMapServiceProvider provider;
    private final TmMapRepository tmMapRepository;

    public TmMapService(List<IMapUpdateStrategy> updateStrategies, DailyMapServiceProvider provider, TmMapRepository tmMapRepository) {
        this.updateStrategies = updateStrategies;
        this.provider = provider;
        this.tmMapRepository = tmMapRepository;
    }

    /**
     * Get the maps for the given game mode
     */
    public List<TmMap> getMapPool(GameMode gameMode) {
        IDailyMapPickerStrategy<? extends DailyMap> dailyMapService = provider.getDailyMapService(gameMode);
        return dailyMapService.getMapPool();
    }

    public List<TmMap> getFullMaps(GameMode gameMode) {
        MapList mapList = gameMode.getMapList();
        return mapList == null ? Collections.emptyList() : tmMapRepository.findByWrTimeIsNotNullAndMapList(mapList);
    }

    /**
     * Fetch maps data from an external API and updated them if needed
     */
    public void fetchAndUpdateMaps() {
        updateStrategies.forEach(IMapUpdateStrategy::fetchAndUpdate);
    }

}
