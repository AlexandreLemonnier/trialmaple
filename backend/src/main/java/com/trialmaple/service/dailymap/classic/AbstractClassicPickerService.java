package com.trialmaple.service.dailymap.classic;

import com.trialmaple.model.dto.projection.MapPickCount;
import com.trialmaple.model.entities.ClassicDailyMap;
import com.trialmaple.model.entities.TmMap;
import com.trialmaple.model.enums.GameMode;
import com.trialmaple.repository.DailyMapRepository;
import com.trialmaple.repository.TmMapRepository;
import com.trialmaple.service.dailymap.IDailyMapPickerStrategy;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public abstract class AbstractClassicPickerService implements IDailyMapPickerStrategy<ClassicDailyMap> {
    protected final TmMapRepository tmMapRepository;
    private final DailyMapRepository dailyMapRepository;
    private final Random random = new Random();

    protected AbstractClassicPickerService(TmMapRepository tmMapRepository, DailyMapRepository dailyMapRepository) {
        this.tmMapRepository = tmMapRepository;
        this.dailyMapRepository = dailyMapRepository;
    }

    @Override
    public ClassicDailyMap pickDailyMap(LocalDate day) {
        List<TmMap> mapPool = this.getMapPool();
        if (mapPool.isEmpty()) {
            log.error("No maps available for {} game mode.", this.getSupportedGameMode());
            return null;
        }

        GameMode gameMode = getSupportedGameMode();
        LocalDate startDate = LocalDate.now().minusDays(10);
        List<TmMap> recentlyPickedMaps = dailyMapRepository.findTmMapsByGameModeAndStartDate(gameMode, startDate);
        Set<Long> recentlyPickedMapIds = recentlyPickedMaps.stream()
                .map(TmMap::getId)
                .collect(Collectors.toSet());

        // Remove recently picked maps from map pool to avoid repeats
        if (recentlyPickedMaps.size() < mapPool.size()) {
            mapPool = mapPool.stream()
                    .filter(map -> !recentlyPickedMapIds.contains(map.getId()))
                    .toList();
        }

        Map<Long, Long> pickCounts = getPickCounts(mapPool);
        TmMap randomMap = pickWeightedRandomMap(mapPool, pickCounts);

        return new ClassicDailyMap(randomMap, day, this.getSupportedGameMode());
    }

    /**
     * Get a map of pick count for each daily map id
     */
    private Map<Long, Long> getPickCounts(List<TmMap> mapPool) {
        List<Long> mapPoolIds = mapPool.stream().map(TmMap::getId).toList();
        LocalDate startDate = LocalDate.now().minusDays(60);
        return dailyMapRepository.countDailyMapsByMapAndStartDate(mapPoolIds, startDate)
                .stream()
                .collect(Collectors.toMap(
                        MapPickCount::mapId,
                        MapPickCount::pickCount
                ));
    }

    /**
     * Pick a random daily map among the map pool, using higher weight for less picked maps
     */
    private TmMap pickWeightedRandomMap(List<TmMap> mapPool, Map<Long, Long> pickCounts) {
        double totalWeight = 0.0;
        List<Double> weights = new ArrayList<>();
        for (TmMap map : mapPool) {
            long pickCount = pickCounts.getOrDefault(map.getId(), 0L);
            double weight = getMapWeight(pickCount);
            weights.add(weight);
            totalWeight += weight;
        }

        double rand = random.nextDouble() * totalWeight;
        double cumulative = 0.0;
        for (int i = 0; i < mapPool.size(); i++) {
            cumulative += weights.get(i);
            if (rand <= cumulative) {
                return mapPool.get(i);
            }
        }

        // Fallback
        return mapPool.getLast();
    }

    /**
     * Compute a map weight
     * @param n number of occurrences
     */
    private double getMapWeight(long n) {
        return 1.0/(n + 1);
    }
}
