package com.trialmaple.tmmap.update;

import org.springframework.stereotype.Service;

import com.trialmaple.external.tmrpg.MapDtoMapper;
import com.trialmaple.external.tmrpg.TmRpgService;
import com.trialmaple.external.tmrpg.MapsResponseDto;
import com.trialmaple.tmmap.MapList;
import com.trialmaple.core.TmGame;
import com.trialmaple.tmmap.TmMapRepository;
import com.trialmaple.tmmap.tmuser.TmUserService;

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
