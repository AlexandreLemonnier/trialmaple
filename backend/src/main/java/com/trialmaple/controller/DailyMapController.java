package com.trialmaple.controller;

import com.trialmaple.exception.InvalidGameModeException;
import com.trialmaple.exception.NoDailyMapFoundException;
import com.trialmaple.model.entities.dailymap.DailyMap;
import com.trialmaple.model.enums.GameMode;
import com.trialmaple.service.dailymap.DailyMapService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/daily-map")
@CrossOrigin(origins = "*")
public class DailyMapController {

    private final DailyMapService dailyMapService;

    public DailyMapController(DailyMapService dailyMapService) {
        this.dailyMapService = dailyMapService;
    }

    /**
     * Get current daily map uuid for the given game mode
     */
    @GetMapping("/uuid")
    public String getDailyMapUuid(@RequestParam String gameMode) throws NoDailyMapFoundException {
        try {
            GameMode gameModeValue = GameMode.valueOf(gameMode);
            DailyMap dailyMap = dailyMapService.getCurrentDailyMap(gameModeValue);
            return dailyMap.getUuid().toString();
        } catch (IllegalArgumentException e) {
            log.error("Invalid game mode.", e);
            throw new InvalidGameModeException(gameMode);
        }
    }
}
