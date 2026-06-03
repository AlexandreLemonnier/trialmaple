package com.trialmaple.controller;

import com.trialmaple.exception.InvalidGameModeException;
import com.trialmaple.model.dto.BlurMapDto;
import com.trialmaple.model.dto.GeoguessrMapDto;
import com.trialmaple.model.dto.TmMapDto;
import com.trialmaple.model.dto.ZoomMapDto;
import com.trialmaple.model.enums.GameMode;
import com.trialmaple.service.maps.TmMapService;
import com.trialmaple.service.picture.BlurPictureService;
import com.trialmaple.service.picture.GeoguessrPictureService;
import com.trialmaple.service.picture.ZoomPictureService;
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
    private final GeoguessrPictureService geoguessrPictureService;
    private final BlurPictureService blurPictureService;
    private final ZoomPictureService zoomPictureService;

    public MapController(TmMapService tmMapService, GeoguessrPictureService geoguessrPictureService, BlurPictureService blurPictureService, ZoomPictureService zoomPictureService) {
        this.tmMapService = tmMapService;
        this.geoguessrPictureService = geoguessrPictureService;
        this.blurPictureService = blurPictureService;
        this.zoomPictureService = zoomPictureService;
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
            return geoguessrPictureService.getMapsName(gameModeValue).stream().map(GeoguessrMapDto::new).toList();
        } catch (IllegalArgumentException e) {
            log.error("Invalid game mode.", e);
            throw new InvalidGameModeException(gameMode);
        }
    }

    /**
     * List maps name for blur game mode
     */
    @GetMapping("/maps/blur")
    public List<BlurMapDto> getBlurMaps(@RequestParam String gameMode) {
        try {
            GameMode gameModeValue = GameMode.valueOf(gameMode);
            return blurPictureService.getMapsName(gameModeValue).stream().map(BlurMapDto::new).toList();
        } catch (IllegalArgumentException e) {
            log.error("Invalid game mode.", e);
            throw new InvalidGameModeException(gameMode);
        }
    }

    /**
     * List maps name for zoom game mode
     */
    @GetMapping("/maps/zoom")
    public List<ZoomMapDto> getZoomMaps(@RequestParam String gameMode) {
        try {
            GameMode gameModeValue = GameMode.valueOf(gameMode);
            return zoomPictureService.getMapsName(gameModeValue).stream().map(ZoomMapDto::new).toList();
        } catch (IllegalArgumentException e) {
            log.error("Invalid game mode.", e);
            throw new InvalidGameModeException(gameMode);
        }
    }
}
