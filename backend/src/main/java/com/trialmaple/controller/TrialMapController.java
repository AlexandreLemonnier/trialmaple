package com.trialmaple.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trialmaple.controller.mappers.TrialMapDtoMapper;
import com.trialmaple.model.dto.TrialMapDto;
import com.trialmaple.model.entities.TrialMap;
import com.trialmaple.service.TrialMapService;

@RestController
@RequestMapping("/api")
public class TrialMapController {

    private final TrialMapService service;
    private final TrialMapDtoMapper trialMapDtoMapper;

    public TrialMapController(TrialMapService service, TrialMapDtoMapper trialMapDtoMapper) {
        this.service = service;
        this.trialMapDtoMapper = trialMapDtoMapper;
    }

    /**
     * Get all maps details
     */
    @GetMapping("/maps")
    public List<TrialMapDto> getAllMaps() {
        List<TrialMap> maps = service.getAllMaps();
        return maps.stream()
                .map(m -> trialMapDtoMapper.serviceToDto(m))
                .collect(Collectors.toList());
    }

    /**
     * List maps name
     * 
     * @return
     */
    @GetMapping("/maps/list")
    public List<String> getAllMapNames() {
        return service.getAllMapNames();
    }

}
