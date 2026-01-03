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
import com.trialmaple.utils.LoggerConstant;
import com.trialmaple.utils.LoggerWrapper;

@Service
public class DailyMapService {
    private static final LoggerWrapper LOGGER = new LoggerWrapper(DailyMapService.class);

    private final List<IDailyMapPickerStrategy> dailyMapPickerStrategies;

    private final DailyMapRepository dailyMapRepository;

    public DailyMapService(List<IDailyMapPickerStrategy> dailyMapPickerStrategies, DailyMapRepository dailyMapRepository) {
        this.dailyMapPickerStrategies = dailyMapPickerStrategies;
        this.dailyMapRepository = dailyMapRepository;
    }

    public DailyMap getCurrentDailyMap() throws NoDailyMapFoundException {
        LocalDate today = LocalDate.now();
        return dailyMapRepository.findByDayAndGameMode(today, GameMode.CLASSIC_TMNF_TRIAL)
                .orElseThrow(() -> new NoDailyMapFoundException(today));
    }

    public void pickAllDailyMapsIfMissing() {
        dailyMapPickerStrategies.forEach(dailyMapPickerService -> pickDailyMapIfMissing(dailyMapPickerService));
    }

    private void pickDailyMapIfMissing(IDailyMapPickerStrategy dailyMapPickerService) {
        GameMode gameMode = dailyMapPickerService.getSupportedGameMode();
        LocalDate today = LocalDate.now();
        boolean exists = dailyMapRepository.existsByDayAndGameMode(today, gameMode);
        if (!exists) {
            List<TmMap> mapPool = dailyMapPickerService.getMapPool();
            if (mapPool.isEmpty()) {
                LOGGER.error("No maps available.", LoggerConstant.GAME_MODE, gameMode);
                return;
            }

            TmMap randomMap = mapPool.get(new Random().nextInt(mapPool.size()));

            DailyMap dailyMap = new DailyMap(randomMap, today, gameMode);

            dailyMapRepository.save(dailyMap);

            LOGGER.info("New daily map chosen.", LoggerConstant.DAY, today, LoggerConstant.GAME_MODE, gameMode, LoggerConstant.MAP_NAME, randomMap.getName());
        }
    }
}