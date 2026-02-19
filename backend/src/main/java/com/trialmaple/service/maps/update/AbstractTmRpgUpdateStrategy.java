package com.trialmaple.service.maps.update;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.trialmaple.controller.mappers.external.MapDtoMapper;
import com.trialmaple.externalservice.tmrpg.TmRpgService;
import com.trialmaple.model.dto.external.tmrpg.MapDto;
import com.trialmaple.model.dto.external.tmrpg.MapsResponseDto;
import com.trialmaple.model.entities.TmMap;
import com.trialmaple.model.entities.TmUser;
import com.trialmaple.repository.TmMapRepository;
import com.trialmaple.service.maps.TmUserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public abstract class AbstractTmRpgUpdateStrategy implements IMapUpdateStrategy {
    protected final TmRpgService tmRpgService;
    protected final TmUserService tmUserService;
    protected final TmMapRepository tmMapRepository;
    protected final MapDtoMapper mapDtoMapper;

    protected AbstractTmRpgUpdateStrategy(TmRpgService tmRpgService, TmUserService tmUserService, TmMapRepository tmMapRepository, MapDtoMapper mapDtoMapper) {
        this.tmRpgService = tmRpgService;
        this.tmUserService = tmUserService;
        this.tmMapRepository = tmMapRepository;
        this.mapDtoMapper = mapDtoMapper;
    }

    abstract MapsResponseDto fetchMaps();

    abstract boolean isClassic(Long mapId);

    /**
     * Anything that needs to be done before fetch and update
     */
    protected void preProcess() {
    }

    @Override
    public void fetchAndUpdate() {
        log.info("Updating maps for {}", getSupportedList());
        try {
            preProcess();
            MapsResponseDto response = fetchMaps();
            List<MapDto> maps = response.maps().stream().map(MapDto::fixMap).toList();

            List<Long> externalMapIds = maps.stream().map(MapDto::id).toList();
            Map<Long, TmMap> existingMaps = tmMapRepository.findAllByTmxIdInAndMapList(externalMapIds, getSupportedList())
                .stream()
                .collect(Collectors.toMap(TmMap::getTmxId, Function.identity()));
            
            List<TmMap> toCreate = new ArrayList<>();
            List<TmMap> toUpdate = new ArrayList<>();
            for (MapDto map : maps) {
                TmUser wrHolder = tmUserService.getOrCreate(map.wrHolder().id(), map.wrHolder().name(), getSupportedGame());
                boolean classic = isClassic(map.id());

                TmMap existingMap = existingMaps.get(map.id());
                if (existingMap == null) {
                    TmMap mapToAdd = mapDtoMapper.externalToService(map, getSupportedList(), wrHolder, classic);
                    // Set not active (for manual check and update if needed)
                    mapToAdd.setActive(false);
                    toCreate.add(mapToAdd);
                } else {
                    boolean updated = mapDtoMapper.update(existingMap, map, wrHolder, classic);
                    if (updated) {
                        log.info("Map updated: {}", existingMap.getName());
                        toUpdate.add(existingMap);
                    }
                }
            }
            tmMapRepository.saveAll(toUpdate);
            tmMapRepository.saveAll(toCreate);
            if (!toCreate.isEmpty()) {
                // Error log to be notified by email
                String addedMapNames = toCreate.stream().map(TmMap::getName).collect(Collectors.joining(", "));
                log.error("New map(s) added to {} list: {}", getSupportedList(), addedMapNames);
            }
        } catch (Exception e) {
            log.error("Error while fetching maps", e);
        }
    }
}
