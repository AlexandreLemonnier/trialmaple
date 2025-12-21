package com.trialmaple.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trialmaple.exception.InvalidMapNameException;
import com.trialmaple.exception.NoDailyMapFoundException;
import com.trialmaple.model.dto.GuessDto;
import com.trialmaple.model.dto.GuessRequestDto;
import com.trialmaple.model.entities.DailyMap;
import com.trialmaple.service.DailyMapService;
import com.trialmaple.service.GuessService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class GuessController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GuessController.class);

    private final DailyMapService dailyMapService;
    private final GuessService guessService;

    public GuessController(GuessService guessService, DailyMapService dailyMapService) {
        this.guessService = guessService;
        this.dailyMapService = dailyMapService;
    }

    @PostMapping("/guess")
    public GuessDto guess(@RequestBody GuessRequestDto request, HttpServletRequest httpRequest)
            throws NoDailyMapFoundException, InvalidMapNameException {
        // temporary code
        LOGGER.info("Guess from IP : " + httpRequest.getRemoteAddr());
        String xff = httpRequest.getHeader("X-Forwarded-For");
        if (xff != null && !xff.isEmpty()) {
            LOGGER.info("IP through XFF : " + xff.split(",")[0].trim());
        }
        // end temporary code
        DailyMap dailyMap = dailyMapService.getCurrentDailyMap();
        return guessService.checkGuess(dailyMap, request);
    }

    @GetMapping("/guess/daily-map")
    public String getDailyMapUuid() throws NoDailyMapFoundException {
        DailyMap dailyMap = dailyMapService.getCurrentDailyMap();
        return dailyMap.getUuid().toString();
    }
}