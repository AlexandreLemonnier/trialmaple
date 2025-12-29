package com.trialmaple.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trialmaple.service.maps.TmMapService;

@RestController
@RequestMapping("/api")
public class MapController {

    private final TmMapService service;

    public MapController(TmMapService service) {
        this.service = service;
    }

    /**
     * List maps name
     * 
     * @param finished get only finished maps (wrTime != null)
     * @return
     */
    @GetMapping("/maps/list")
    public List<String> getAllMapNames(
            @RequestParam(name = "finished", required = false, defaultValue = "false") boolean finished) {
        return service.getAllMapNames(finished);
    }

}
