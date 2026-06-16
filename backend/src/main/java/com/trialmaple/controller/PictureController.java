package com.trialmaple.controller;

import com.trialmaple.TmMapleConstant;
import com.trialmaple.config.RouteKey;
import com.trialmaple.exception.InvalidAttemptException;
import com.trialmaple.exception.InvalidGameModeException;
import com.trialmaple.model.entities.dailymap.BlurDailyMap;
import com.trialmaple.model.entities.dailymap.GeoguessrDailyMap;
import com.trialmaple.model.entities.dailymap.ZoomDailyMap;
import com.trialmaple.model.enums.GameMode;
import com.trialmaple.service.dailymap.DailyMapService;
import com.trialmaple.service.picture.BlurPictureService;
import com.trialmaple.service.picture.GeoguessrPictureService;
import com.trialmaple.service.picture.ZoomPictureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
@RestController
@RequestMapping(RouteKey.BASE_API)
@CrossOrigin(origins = "*")
public class PictureController {

    private final DailyMapService dailyMapService;
    private final GeoguessrPictureService geoguessrPictureService;
    private final BlurPictureService blurPictureService;
    private final ZoomPictureService zoomPictureService;

    public PictureController(DailyMapService dailyMapService, GeoguessrPictureService geoguessrPictureService, BlurPictureService blurPictureService, ZoomPictureService zoomPictureService) {
        this.dailyMapService = dailyMapService;
        this.geoguessrPictureService = geoguessrPictureService;
        this.blurPictureService = blurPictureService;
        this.zoomPictureService = zoomPictureService;
    }

    /**
     * Get specific picture for given Geoguessr game mode
     */
    @GetMapping(RouteKey.GEOGUESSR_PICTURE)
    public ResponseEntity<Resource> getGeoguessrPicture(@RequestParam String gameMode, @PathVariable String pictureNumberParam) {
        try {
            GameMode gameModeValue = GameMode.valueOf(gameMode);
            GeoguessrDailyMap dailyMap = (GeoguessrDailyMap) dailyMapService.getCurrentDailyMap(gameModeValue);
            int pictureNumber = Integer.parseInt(pictureNumberParam);
            if (pictureNumber <= 0 || pictureNumber > TmMapleConstant.GEOGUESSR_PICTURES_COUNT) {
                throw new InvalidAttemptException(pictureNumberParam);
            }
            Path picturePath = geoguessrPictureService.getTodayPicturePath(gameModeValue, dailyMap, pictureNumber);
            return sendResponse(picturePath);
        } catch (NumberFormatException e) {
            log.error("Invalid attempt.", e);
            throw new InvalidAttemptException(pictureNumberParam);
        } catch (IllegalArgumentException e) {
            log.error("Invalid game mode.", e);
            throw new InvalidGameModeException(gameMode);
        }
    }

    /**
     * Get specific picture for given Blur game mode
     */
    @GetMapping(RouteKey.BLUR_PICTURE)
    public ResponseEntity<Resource> getBlurPicture(@RequestParam String gameMode) {
        try {
            GameMode gameModeValue = GameMode.valueOf(gameMode);
            BlurDailyMap dailyMap = (BlurDailyMap) dailyMapService.getCurrentDailyMap(gameModeValue);

            Path picturePath = blurPictureService.getTodayPicturePath(gameModeValue, dailyMap);
            return sendResponse(picturePath);
        } catch (IllegalArgumentException e) {
            log.error("Invalid game mode.", e);
            throw new InvalidGameModeException(gameMode);
        }
    }

    /**
     * Get specific picture for given Zoom game mode
     */
    @GetMapping(RouteKey.ZOOM_PICTURE)
    public ResponseEntity<Resource> getZoomPicture(@RequestParam String gameMode) {
        try {
            GameMode gameModeValue = GameMode.valueOf(gameMode);
            ZoomDailyMap dailyMap = (ZoomDailyMap) dailyMapService.getCurrentDailyMap(gameModeValue);

            Path picturePath = zoomPictureService.getTodayPicturePath(gameModeValue, dailyMap);
            return sendResponse(picturePath);
        } catch (IllegalArgumentException e) {
            log.error("Invalid game mode.", e);
            throw new InvalidGameModeException(gameMode);
        }
    }

    /**
     * Returns ResponseEntity with attached picture as Resource if it exists
     */
    private ResponseEntity<Resource> sendResponse(Path picturePath) throws ResponseStatusException {
        try {
            if (!Files.exists(picturePath)) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }

            Resource resource = new FileSystemResource(picturePath);

            String contentType = Files.probeContentType(picturePath);
            if (contentType == null) {
                contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);
        } catch (IOException e) {
            log.error("Error reading file.", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
