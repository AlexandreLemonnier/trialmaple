package com.trialmaple.tmmap.update;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.trialmaple.external.tmrpg.MapDtoMapper;
import com.trialmaple.external.tmrpg.TmRpgService;
import com.trialmaple.external.tmrpg.MapDto;
import com.trialmaple.external.tmrpg.MapsResponseDto;
import com.trialmaple.tmmap.MapList;
import com.trialmaple.core.TmGame;
import com.trialmaple.tmmap.TmMapRepository;
import com.trialmaple.tmmap.tmuser.TmUserService;

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
