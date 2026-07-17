package com.trialmaple.tmmap;

import com.trialmaple.core.config.RouteKey;
import com.trialmaple.core.exception.InvalidGameModeException;
import com.trialmaple.core.GameMode;
import com.trialmaple.picture.BlurPictureService;
import com.trialmaple.picture.GeoguessrPictureService;
import com.trialmaple.picture.ZoomPictureService;
import com.trialmaple.tmmap.dto.BlurMapDto;
import com.trialmaple.tmmap.dto.GeoguessrMapDto;
import com.trialmaple.tmmap.dto.TmMapDto;
import com.trialmaple.tmmap.dto.ZoomMapDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(RouteKey.MAPS_PREFIX)
public class TmMapController {

    private final TmMapService tmMapService;
    private final GeoguessrPictureService geoguessrPictureService;
    private final BlurPictureService blurPictureService;
    private final ZoomPictureService zoomPictureService;

    public TmMapController(TmMapService tmMapService, GeoguessrPictureService geoguessrPictureService, BlurPictureService blurPictureService, ZoomPictureService zoomPictureService) {
        this.tmMapService = tmMapService;
        this.geoguessrPictureService = geoguessrPictureService;
        this.blurPictureService = blurPictureService;
        this.zoomPictureService = zoomPictureService;
    }

    /**
     * List maps for a specific game mode
     */
    @GetMapping(RouteKey.MAPS_LIST)
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
    @GetMapping(RouteKey.GEOGUESSR_MAPS)
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
    @GetMapping(RouteKey.BLUR_MAPS)
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
    @GetMapping(RouteKey.ZOOM_MAPS)
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
