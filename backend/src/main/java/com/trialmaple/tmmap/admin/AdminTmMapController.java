package com.trialmaple.tmmap.admin;

import com.trialmaple.core.GameMode;
import com.trialmaple.core.config.RouteKey;
import com.trialmaple.core.exception.InvalidGameModeException;
import com.trialmaple.tmmap.TmMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(RouteKey.ADMIN_PREFIX + RouteKey.MAPS_PREFIX)
@CrossOrigin(origins = "*")
@Slf4j
public class AdminTmMapController {

    private final AdminTmMapService adminTmMapService;
    private final AdminTmMapDtoMapper adminTmMapDtoMapper;

    public AdminTmMapController(AdminTmMapService adminTmMapService, AdminTmMapDtoMapper adminTmMapDtoMapper) {
        this.adminTmMapService = adminTmMapService;
        this.adminTmMapDtoMapper = adminTmMapDtoMapper;
    }

    @GetMapping("")
    public List<AdminTmMapDto> getMaps(@RequestParam String gameMode) {
        try {
            GameMode gameModeValue = GameMode.valueOf(gameMode);
            List<TmMap> maps = adminTmMapService.getFullMaps(gameModeValue);
            return maps.stream().map(adminTmMapDtoMapper::serviceToDto).toList();
        } catch (IllegalArgumentException e) {
            log.error("Invalid game mode.", e);
            throw new InvalidGameModeException(gameMode);
        }
    }

    @PutMapping("")
    public ResponseEntity<Void> updateMaps(@RequestBody List<AdminTmMapDto> request) {

        if (request == null || request.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        adminTmMapService.updateMapsBatch(request);

        return ResponseEntity.ok().build();
    }
}
