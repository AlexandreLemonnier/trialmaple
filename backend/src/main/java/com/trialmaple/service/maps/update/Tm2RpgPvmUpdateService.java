package com.trialmaple.service.maps.update;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.trialmaple.controller.mappers.external.MapDtoMapper;
import com.trialmaple.externalservice.tmrpg.TmRpgService;
import com.trialmaple.model.dto.external.tmrpg.MapDto;
import com.trialmaple.model.dto.external.tmrpg.MapsResponseDto;
import com.trialmaple.model.enums.MapList;
import com.trialmaple.model.enums.TmGame;
import com.trialmaple.repository.TmMapRepository;
import com.trialmaple.service.maps.TmUserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class Tm2RpgPvmUpdateService extends AbstractTmRpgUpdateStrategy {

    private List<Long> classicMapIds = new ArrayList<>();

    public Tm2RpgPvmUpdateService(TmRpgService tmRpgService, TmUserService tmUserService, TmMapRepository tmMapRepository, MapDtoMapper mapDtoMapper) {
        super(tmRpgService, tmUserService, tmMapRepository, mapDtoMapper);
    }

    @Override
    public MapList getSupportedList() {
        return MapList.TM2_RPG_PVM;
    }

    @Override
    public TmGame getSupportedGame() {
        return TmGame.TM2;
    }

    @Override
    protected void preProcess() {
        MapsResponseDto classicResponse = tmRpgService.getTm2RpgClassicMaps();
        classicMapIds = classicResponse.maps().stream().map(MapDto::id).toList();
    }

    @Override
    MapsResponseDto fetchMaps() {
        return tmRpgService.getTm2RpgPvmMaps();
    }

    @Override
    boolean isClassic(Long mapId) {
        return classicMapIds.contains(mapId);
    }
}
