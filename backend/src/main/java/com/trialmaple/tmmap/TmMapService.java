package com.trialmaple.tmmap;

import com.trialmaple.core.GameMode;
import com.trialmaple.dailymap.DailyMap;
import com.trialmaple.dailymap.DailyMapServiceProvider;
import com.trialmaple.dailymap.IDailyMapPickerStrategy;
import com.trialmaple.tmmap.update.IMapUpdateStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TmMapService {
    private final List<IMapUpdateStrategy> updateStrategies;
    private final DailyMapServiceProvider provider;

    /**
     * Get the maps for the given game mode
     */
    public List<TmMap> getMapPool(GameMode gameMode) {
        IDailyMapPickerStrategy<? extends DailyMap> dailyMapService = provider.getDailyMapService(gameMode);
        return dailyMapService.getMapPool();
    }

    /**
     * Fetch maps data from an external API and updated them if needed
     */
    public void fetchAndUpdateMaps() {
        updateStrategies.forEach(IMapUpdateStrategy::fetchAndUpdate);
    }

}
