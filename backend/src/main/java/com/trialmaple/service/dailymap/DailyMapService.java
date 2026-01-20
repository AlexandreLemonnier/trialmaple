package com.trialmaple.service.dailymap;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.trialmaple.exception.NoDailyMapFoundException;
import com.trialmaple.model.dto.projection.MapPickCount;
import com.trialmaple.model.entities.DailyMap;
import com.trialmaple.model.entities.TmMap;
import com.trialmaple.model.enums.GameMode;
import com.trialmaple.repository.DailyMapRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DailyMapService {
    private final List<IDailyMapPickerStrategy> dailyMapPickerStrategies;

    private final DailyMapRepository dailyMapRepository;

    private final Random random = new Random();

    public DailyMapService(List<IDailyMapPickerStrategy> dailyMapPickerStrategies, DailyMapRepository dailyMapRepository) {
        this.dailyMapPickerStrategies = dailyMapPickerStrategies;
        this.dailyMapRepository = dailyMapRepository;
    }

    /**
     * Returns current daily map for given game mode
     */
    public DailyMap getCurrentDailyMap(GameMode gameMode) throws NoDailyMapFoundException {
        LocalDate today = LocalDate.now();
        return dailyMapRepository.findByDayAndGameMode(today, gameMode)
                .orElseThrow(() -> new NoDailyMapFoundException(today));
    }

    /**
     * Pick a new daily map for all game modes if absent
     */
    public void pickAllDailyMapsIfMissing() {
        dailyMapPickerStrategies.forEach(this::pickDailyMapIfMissing);
    }

    /**
     * Pick a new daily map if absent with the given map picker
     */
    private void pickDailyMapIfMissing(IDailyMapPickerStrategy dailyMapPickerService) {
        GameMode gameMode = dailyMapPickerService.getSupportedGameMode();
        LocalDate today = LocalDate.now();
        boolean exists = dailyMapRepository.existsByDayAndGameMode(today, gameMode);
        if (!exists) {
            List<TmMap> mapPool = dailyMapPickerService.getMapPool();
            if (mapPool.isEmpty()) {
                log.error("No maps available for {} game mode.", gameMode);
                return;
            }

            Map<Long, Long> pickCounts = getPickCounts(mapPool);
            TmMap randomMap = pickWeightedRandomMap(mapPool, pickCounts);

            DailyMap dailyMap = new DailyMap(randomMap, today, gameMode);

            dailyMapRepository.save(dailyMap);

            log.info("New daily map chosen for {} game mode: {}.", gameMode, randomMap.getName());
        }
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
        if (mapPool.size() == pickCounts.keySet().size()) {
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
        return mapPool.get(mapPool.size() - 1);
    }

    /**
     * Compute a map weight
     * @param n number of occurences
     * @param offset value subtracted from n to shift it toward zero
     */
    private double getMapWeight(long n, long offset) {
        return 1/Math.sqrt(Math.max(n - offset, 0) + 1.0);
    }
}