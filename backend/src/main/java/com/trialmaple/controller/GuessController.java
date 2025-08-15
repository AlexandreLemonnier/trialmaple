package com.trialmaple.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trialmaple.model.dto.GuessDto;
import com.trialmaple.model.entities.DailyMap;
import com.trialmaple.service.DailyMapService;
import com.trialmaple.service.GuessService;

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
    public GuessDto guess(@RequestParam String guess, @RequestParam int guessNumber) {
        // TODO Create and throw custom exception instead of IllegalStateException
        DailyMap dailyMap = dailyMapService.getCurrentDailyMap();
        return guessService.checkGuess(dailyMap, guess, guessNumber);
    }
}