package com.trialmaple.dailymap;

import com.trialmaple.core.config.RouteKey;
import com.trialmaple.core.exception.InvalidGameModeException;
import com.trialmaple.core.exception.NoDailyMapFoundException;
import com.trialmaple.core.GameMode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(RouteKey.DAILY_MAP_PREFIX)
@CrossOrigin(origins = "*")
public class DailyMapController {

    private final DailyMapService dailyMapService;

    public DailyMapController(DailyMapService dailyMapService) {
        this.dailyMapService = dailyMapService;
    }

    /**
     * Get current daily map uuid for the given game mode
     */
    @GetMapping(RouteKey.DAILY_MAP_UUID)
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
