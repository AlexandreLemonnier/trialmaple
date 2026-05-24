package com.trialmaple.service.maps.update;

import com.trialmaple.controller.mappers.external.MapDtoMapper;
import com.trialmaple.externalservice.tmrpg.TmRpgService;
import com.trialmaple.model.dto.external.tmrpg.MapDto;
import com.trialmaple.model.dto.external.tmrpg.MapsResponseDto;
import com.trialmaple.model.entities.TmMap;
import com.trialmaple.model.enums.MapList;
import com.trialmaple.model.enums.TmGame;
import com.trialmaple.repository.TmMapRepository;
import com.trialmaple.service.maps.TmUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class Tm2020RpgUpdateService extends AbstractTmRpgUpdateStrategy {

    private static final int MIN_STARS_VALUE = 3;

    public Tm2020RpgUpdateService(TmRpgService tmRpgService, TmUserService tmUserService, TmMapRepository tmMapRepository, MapDtoMapper mapDtoMapper) {
        super(tmRpgService, tmUserService, tmMapRepository, mapDtoMapper);
    }

    @Override
    public MapList getSupportedList() {
        return MapList.TM2020_RPG;
    }

    @Override
    public TmGame getSupportedGame() {
        return TmGame.TM2020;
    }

    @Override
    MapsResponseDto fetchMaps() {
        return tmRpgService.getTm2020RpgMaps();
    }

    @Override
    boolean isClassic(Long mapId) {
        return true;
    }

    @Override
    protected boolean specificUpdate(TmMap existingMap, MapDto updatedMap) {
        boolean hasLostMinStars = existingMap.getPoints() >= MIN_STARS_VALUE && updatedMap.stars() < MIN_STARS_VALUE;
        boolean hasGainedMinStars = existingMap.getPoints() < MIN_STARS_VALUE && updatedMap.stars() >= MIN_STARS_VALUE;
        // Enable map if it has at least MIN_STARS_VALUE stars (minimum requirement for being in the map pool)
        if (hasLostMinStars || hasGainedMinStars) {
            String logFormat = "{} {}: {} -> {}";
            log.info(logFormat, existingMap.getName(), "active", existingMap.isActive(), hasGainedMinStars);
            existingMap.setActive(hasGainedMinStars);
            return true;
        }
        return false;
    }
}