package com.trialmaple.controller;

import com.trialmaple.TmMapleConstant;
import com.trialmaple.exception.InvalidAttemptException;
import com.trialmaple.exception.InvalidGameModeException;
import com.trialmaple.model.entities.DailyMap;
import com.trialmaple.model.enums.GameMode;
import com.trialmaple.service.dailymap.DailyMapService;
import com.trialmaple.service.maps.PictureService;
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
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class PictureController {

    private final DailyMapService dailyMapService;
    private final PictureService pictureService;

    public PictureController(DailyMapService dailyMapService, PictureService pictureService) {
        this.dailyMapService = dailyMapService;
        this.pictureService = pictureService;
    }

    /**
     * Get specific picture for given game mode
     */
    @GetMapping("/picture/{attempt}")
    public ResponseEntity<Resource> getPicture(@RequestParam String gameMode, @PathVariable String attempt) {
        try {
            GameMode gameModeValue = GameMode.valueOf(gameMode);
            DailyMap dailyMap = dailyMapService.getCurrentDailyMap(gameModeValue);
            int attemptValue = Integer.parseInt(attempt);
            if (attemptValue <= 0 || attemptValue > TmMapleConstant.GEOGUESSR_PICTURES_COUNT) {
                throw new InvalidAttemptException(attempt);
            }
            Path picturePath = pictureService.getTodayPicturePath(gameModeValue, dailyMap, attemptValue);
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
        } catch (NumberFormatException e) {
            log.error("Invalid attempt.", e);
            throw new InvalidAttemptException(attempt);
        } catch (IllegalArgumentException e) {
            log.error("Invalid game mode.", e);
            throw new InvalidGameModeException(gameMode);
        } catch (IOException e) {
            log.error("Error reading file.", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
