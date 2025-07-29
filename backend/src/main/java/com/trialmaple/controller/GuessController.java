package com.trialmaple.controller;

import com.trialmaple.model.TrialMap;
import com.trialmaple.service.DailyMapService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class GuessController {

    /*
    private final DailyMapService dailyMapService;

    public GuessController(DailyMapService dailyMapService) {
        this.dailyMapService = dailyMapService;
    }

    
    @PostMapping("/guess")
    public Map<String, Object> guess(@RequestParam String name) {
        TrialMap today = dailyMapService.getTodayMap();
        Map<String, Object> result = new HashMap<>();

        boolean correct = today.getAcceptedAnswers().stream()
            .anyMatch(ans -> ans.equalsIgnoreCase(name.trim()));

        result.put("correct", correct);
        result.put("feedback", Map.of(
            "mapper", name.equalsIgnoreCase(today.getMapper()),
            "checkpoints", today.getCheckpoints(),
            "difficulty", today.getDifficulty()
        ));

        return result;
    }

    @GetMapping("/map/info")
    public Map<String, Object> getTodayInfo() {
        TrialMap today = dailyMapService.getTodayMap();
        return Map.of("mapper", today.getMapper(), "checkpoints", today.getCheckpoints(), "difficulty", today.getDifficulty());
    }
        */
}