package com.trialmaple.tmmap.admin;

import com.trialmaple.core.GameMode;
import com.trialmaple.core.config.RouteKey;
import com.trialmaple.core.exception.InvalidGameModeException;
import com.trialmaple.tmmap.TmMap;
import com.trialmaple.tmmap.TmMapService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(RouteKey.ADMIN_PREFIX + RouteKey.MAPS_PREFIX)
@CrossOrigin(origins = "*")
@Slf4j
public class AdminTmMapController {

    private final TmMapService tmMapService;
    private final AdminTmMapDtoMapper adminTmMapDtoMapper;

    public AdminTmMapController(TmMapService tmMapService, AdminTmMapDtoMapper adminTmMapDtoMapper) {
        this.tmMapService = tmMapService;
        this.adminTmMapDtoMapper = adminTmMapDtoMapper;
    }

    @GetMapping("")
    public List<AdminTmMapDto> getMaps(@RequestParam String gameMode) {
        try {
            GameMode gameModeValue = GameMode.valueOf(gameMode);
            List<TmMap> maps = tmMapService.getFullMaps(gameModeValue);
            return maps.stream().map(adminTmMapDtoMapper::serviceToDto).toList();
        } catch (IllegalArgumentException e) {
            log.error("Invalid game mode.", e);
            throw new InvalidGameModeException(gameMode);
        }
    }
}
