package com.trialmaple.service.dailymap.classic;

import com.trialmaple.model.dto.projection.MapPickCount;
import com.trialmaple.model.entities.DailyMap;
import com.trialmaple.model.entities.TmMap;
import com.trialmaple.repository.DailyMapRepository;
import com.trialmaple.repository.TmMapRepository;
import com.trialmaple.service.dailymap.IDailyMapPickerStrategy;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Slf4j
public abstract class AbstractClassicPickerService implements IDailyMapPickerStrategy {
    protected final TmMapRepository tmMapRepository;
    private final DailyMapRepository dailyMapRepository;
    private final Random random = new Random();

    protected AbstractClassicPickerService(TmMapRepository tmMapRepository, DailyMapRepository dailyMapRepository) {
        this.tmMapRepository = tmMapRepository;
        this.dailyMapRepository = dailyMapRepository;
    }

    @Override
    public DailyMap pickDailyMap(LocalDate day) {
        List<TmMap> mapPool = this.getMapPool();
        if (mapPool.isEmpty()) {
            log.error("No maps available for {} game mode.", this.getSupportedGameMode());
            return null;
        }

        Map<Long, Long> pickCounts = getPickCounts(mapPool);
        TmMap randomMap = pickWeightedRandomMap(mapPool, pickCounts);

        return new DailyMap(randomMap, day, this.getSupportedGameMode());
    }

    /**
     * Get a map of pick count for each daily map id
     */
    private Map<Long, Long> getPickCounts(List<TmMap> mapPool) {
        List<Long> mapPoolIds = mapPool.stream().map(TmMap::getId).toList();
        return dailyMapRepository.countDailyMapsByMap(mapPoolIds)
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
        long minPickCount;
        if (mapPool.size() == pickCounts.size()) {
            // All maps have been picked at least once (all present in the Map)
            minPickCount = pickCounts.values().stream().min(Long::compareTo).orElse(0L);
        } else {
            // Some maps have not been picked yet, so min is 0
            minPickCount = 0L;
        }
        for (TmMap map : mapPool) {
            long n = pickCounts.getOrDefault(map.getId(), 0L);
            double weight = getMapWeight(n, minPickCount);
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
     * @param offset value subtracted from n to shift it toward zero
     */
    private double getMapWeight(long n, long offset) {
        return 1/Math.sqrt(Math.max(n - offset, 0) + 1.0);
    }
}
