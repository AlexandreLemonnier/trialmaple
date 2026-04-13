package com.trialmaple.service.dailymap;

import com.trialmaple.exception.NoDailyMapFoundException;
import com.trialmaple.model.entities.DailyMap;
import com.trialmaple.model.enums.GameMode;
import com.trialmaple.repository.DailyMapRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class DailyMapService {
    private final List<IDailyMapPickerStrategy> dailyMapPickerStrategies;

    private final DailyMapRepository dailyMapRepository;

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
            DailyMap dailyMap = dailyMapPickerService.pickDailyMap(today);
            if (dailyMap == null) {
                return;
            }
            dailyMapRepository.save(dailyMap);
            String name = dailyMap.getMap() != null ? dailyMap.getMap().getName() : dailyMap.getDailyPictures().getMapName();
            log.info("New daily map chosen for {} game mode: {}.", gameMode, name);
        }
    }
}