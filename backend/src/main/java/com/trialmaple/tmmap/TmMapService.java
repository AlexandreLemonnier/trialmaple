package com.trialmaple.tmmap;

import java.util.List;

import com.trialmaple.tmmap.dto.TmMapDto;
import org.springframework.stereotype.Service;

import com.trialmaple.dailymap.DailyMap;
import com.trialmaple.core.GameMode;
import com.trialmaple.dailymap.DailyMapServiceProvider;
import com.trialmaple.dailymap.IDailyMapPickerStrategy;
import com.trialmaple.tmmap.update.IMapUpdateStrategy;

@Service
public class TmMapService {
    private final List<IMapUpdateStrategy> updateStrategies;
    private final DailyMapServiceProvider provider;
    private final TmMapDtoMapper tmMapDtoMapper;

    public TmMapService(List<IMapUpdateStrategy> updateStrategies, DailyMapServiceProvider provider, TmMapDtoMapper tmMapDtoMapper) {
        this.updateStrategies = updateStrategies;
        this.provider = provider;
        this.tmMapDtoMapper = tmMapDtoMapper;
    }

    /**
     * Get the maps for the given game mode
     */
    public List<TmMapDto> getAllMaps(GameMode gameMode) {
        IDailyMapPickerStrategy<? extends DailyMap> dailyMapService = provider.getDailyMapService(gameMode);
        List<TmMap> maps = dailyMapService.getMapPool();
        return maps.stream().map(tmMapDtoMapper::serviceToDto).toList();
    }

    /**
     * Fetch maps data from an external API and updated them if needed
     */
    public void fetchAndUpdateMaps() {
        updateStrategies.forEach(IMapUpdateStrategy::fetchAndUpdate);
    }

}
