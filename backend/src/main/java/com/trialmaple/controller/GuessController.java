package com.trialmaple.controller;

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
import com.trialmaple.service.GuessService;
import com.trialmaple.service.dailymap.DailyMapService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class GuessController {

    private final DailyMapService dailyMapService;
    private final GuessService guessService;

    public GuessController(GuessService guessService, DailyMapService dailyMapService) {
        this.guessService = guessService;
        this.dailyMapService = dailyMapService;
    }

    @PostMapping("/guess")
    public GuessDto guess(@RequestBody GuessRequestDto request)
            throws NoDailyMapFoundException, InvalidMapNameException {
        DailyMap dailyMap = dailyMapService.getCurrentDailyMap();
        return guessService.checkGuess(dailyMap, request);
    }

    @GetMapping("/guess/daily-map")
    public String getDailyMapUuid() throws NoDailyMapFoundException {
        DailyMap dailyMap = dailyMapService.getCurrentDailyMap();
        return dailyMap.getUuid().toString();
    }
}