package com.trialmaple.service.maps.update;

import org.springframework.stereotype.Service;

import com.trialmaple.controller.mappers.external.MapDtoMapper;
import com.trialmaple.externalservice.tmrpg.TmRpgService;
import com.trialmaple.model.dto.external.tmrpg.MapsResponseDto;
import com.trialmaple.model.enums.MapList;
import com.trialmaple.model.enums.TmGame;
import com.trialmaple.repository.TmMapRepository;
import com.trialmaple.service.maps.TmUserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TmnfRpgClassicUpdateService extends AbstractTmRpgUpdateStrategy {

    public TmnfRpgClassicUpdateService(TmRpgService tmRpgService, TmUserService tmUserService, TmMapRepository tmMapRepository, MapDtoMapper mapDtoMapper) {
        super(tmRpgService, tmUserService, tmMapRepository, mapDtoMapper);
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
    MapsResponseDto fetchMaps() {
        return tmRpgService.getTmnfRpgClassicMaps();
    }

    @Override
    boolean isClassic(Long mapId) {
        return true;
    }      
}
