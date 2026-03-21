package com.trialmaple.service.maps;

import com.trialmaple.controller.mappers.external.TmMapDtoMapper;
import com.trialmaple.model.dto.TmMapDto;
import com.trialmaple.model.entities.TmMap;
import com.trialmaple.model.enums.GameMode;
import com.trialmaple.service.dailymap.DailyMapServiceProvider;
import com.trialmaple.service.dailymap.IDailyMapPickerStrategy;
import com.trialmaple.service.maps.update.IMapUpdateStrategy;
import org.springframework.stereotype.Service;

import java.util.List;

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
        IDailyMapPickerStrategy dailyMapService = provider.getDailyMapService(gameMode);
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
