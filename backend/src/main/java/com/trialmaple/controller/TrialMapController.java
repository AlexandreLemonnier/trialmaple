package com.trialmaple.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trialmaple.model.TrialMap;
import com.trialmaple.model.dto.TrialMapDto;
import com.trialmaple.service.TrialMapService;
import com.trialmaple.utils.TimeUtils;

@RestController
@RequestMapping("/api/maps")
public class TrialMapController {

    private final TrialMapService service;

    public TrialMapController(TrialMapService service) {
        this.service = service;
    }

    @GetMapping
    public List<TrialMapDto> getAllMaps() {
        List<TrialMap> maps = service.getAllMaps();
        // TODO entity to DTO
        return maps.stream()
                .map(m -> new TrialMapDto(
                        m.getName(),
                        m.getAuthors(),
                        m.getNbCheckpoints(),
                        m.getDifficulty().name(),
                        m.getPoints(),
                        m.getNbFinishers(),
                        m.getAcceptedAnswers(),
                        TimeUtils.formatDuration(m.getWorldRecord())))
                .collect(Collectors.toList());
    }

}
