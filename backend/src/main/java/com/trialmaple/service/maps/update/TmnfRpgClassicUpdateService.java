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
import com.trialmaple.model.enums.MapList;
import com.trialmaple.model.enums.TmGame;
import com.trialmaple.repository.TmMapRepository;
import com.trialmaple.service.maps.TmUserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TmnfRpgClassicUpdateService implements IMapUpdateStrategy {

    private final TmRpgService tmRpgService;
    private final TmUserService tmUserService;
    private final TmMapRepository tmMapRepository;
    private final MapDtoMapper mapDtoMapper;

    public TmnfRpgClassicUpdateService(TmRpgService tmRpgService, TmUserService tmUserService, TmMapRepository tmMapRepository, MapDtoMapper mapDtoMapper) {
        this.tmRpgService = tmRpgService;
        this.tmUserService = tmUserService;
        this.tmMapRepository = tmMapRepository;
        this.mapDtoMapper = mapDtoMapper;
    }

    @Override
    public MapList getSupportedList() {
        return MapList.TMNF_RPG_CLASSIC;
    }

    @Override
    public TmGame getSupportedGame() {
        return TmGame.TMNF;
    }

    @Override
    public void fetchAndUpdate() {
        log.info("Updating maps for {}", getSupportedList());
        try {
            MapsResponseDto response = tmRpgService.getTmnfRpgClassicMaps();
            List<MapDto> maps = response.maps().stream().map(MapDto::fixCpsCount).toList();

            List<String> externalMapNames = maps.stream().map(MapDto::name).toList();
            Map<String, TmMap> existingMaps = tmMapRepository.findAllByNameInAndMapList(externalMapNames, getSupportedList())
                .stream()
                .collect(Collectors.toMap(TmMap::getName, Function.identity()));

            List<TmMap> toUpdate = new ArrayList<>();

            for (MapDto map : maps) {
                TmUser wrHolder = tmUserService.getOrCreate(map.wrHolder().id(), map.wrHolder().name(), getSupportedGame());

                TmMap existingMap = existingMaps.get(map.name());
                if (existingMap == null) {
                    // Error log to be notified by email
                    // Use this to initialize data on first fetch: toCreate.add(mapDtoMapper.externalToService(map, getSupportedList(), wrHolder));
                    // tmMapRepository.saveAll(toCreate);
                    log.error("New map to add to {} list: {}", getSupportedList(), map.name());
                } else {
                    boolean updated = mapDtoMapper.update(existingMap, map, wrHolder);
                    if (updated) {
                        log.info("Map updated: {}", existingMap.getName());
                        toUpdate.add(existingMap);
                    }
                }
            }
            tmMapRepository.saveAll(toUpdate);
        } catch (Exception e) {
            log.error("Error while fetching maps", e);
        }
    }
    
}
