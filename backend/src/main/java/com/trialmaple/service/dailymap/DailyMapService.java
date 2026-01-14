package com.trialmaple.service.dailymap;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.trialmaple.exception.NoDailyMapFoundException;
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

            TmMap randomMap = mapPool.get(this.random.nextInt(mapPool.size()));

            DailyMap dailyMap = new DailyMap(randomMap, today, gameMode);

            dailyMapRepository.save(dailyMap);

            log.info("New daily map chosen for {} game mode: {}.", gameMode, randomMap.getName());
        }
    }
}