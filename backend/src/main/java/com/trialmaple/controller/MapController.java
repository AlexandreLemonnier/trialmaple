package com.trialmaple.controller;

import com.trialmaple.exception.InvalidGameModeException;
import com.trialmaple.model.dto.GeoguessrMapDto;
import com.trialmaple.model.dto.TmMapDto;
import com.trialmaple.model.enums.GameMode;
import com.trialmaple.service.maps.PictureService;
import com.trialmaple.service.maps.TmMapService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class MapController {

    private final TmMapService tmMapService;
    private final PictureService pictureService;

    public MapController(TmMapService tmMapService, PictureService pictureService) {
        this.tmMapService = tmMapService;
        this.pictureService = pictureService;
    }

    /**
     * List maps for a specific game mode
     */
    @GetMapping("/maps/list")
    public List<TmMapDto> getActiveMaps(@RequestParam String gameMode) {
        try {
            GameMode gameModeValue = GameMode.valueOf(gameMode);
            return tmMapService.getAllMaps(gameModeValue);
        } catch (IllegalArgumentException e) {
            log.error("Invalid game mode.", e);
            throw new InvalidGameModeException(gameMode);
        }
    }

    /**
     * List maps name for geoguessr game mode
     */
    @GetMapping("/maps/geoguessr")
    public List<GeoguessrMapDto> getGeoguessrMaps(@RequestParam String gameMode) {
        try {
            GameMode gameModeValue = GameMode.valueOf(gameMode);
            return pictureService.getMapsName(gameModeValue).stream().map(GeoguessrMapDto::new).toList();
        } catch (IllegalArgumentException e) {
            log.error("Invalid game mode.", e);
            throw new InvalidGameModeException(gameMode);
        }
    }
}
