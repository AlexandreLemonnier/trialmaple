package com.trialmaple.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trialmaple.exception.InvalidGameModeException;
import com.trialmaple.model.dto.TmMapDto;
import com.trialmaple.model.enums.GameMode;
import com.trialmaple.service.maps.TmMapService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
public class MapController {

    private final TmMapService service;

    public MapController(TmMapService service) {
        this.service = service;
    }

    /**
     * List maps for a specific game mode
     */
    @GetMapping("/maps/list")
    public List<TmMapDto> getAllMapNames(@RequestParam String gameMode) {
        try {
            GameMode gameModeValue = GameMode.valueOf(gameMode);
            return service.getAllMaps(gameModeValue);
        } catch (IllegalArgumentException e) {
            log.error("Invalid game mode.", e);
            throw new InvalidGameModeException(gameMode);
        }
    }

}
