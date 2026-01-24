package com.trialmaple.externalservice.tmrpg;

import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import com.trialmaple.model.dto.external.tmrpg.MapsResponseDto;

/**
 * tmrpg.com is run by Achepta
 */
@HttpExchange(url = "https://tmrpg.com/api", accept = "application/json")
public interface TmRpgService {

    /**
     * Get "TMNF Classic RPG" maps from tmrpg.com
     * @return
     */
    @GetExchange("/game/tmnf/maps?secret=arso&mode=classic")
    MapsResponseDto getTmnfRpgClassicMaps();

    /**
     * Get "TM2 Trial" maps from tmrpg.com
     * @return
     */
    @GetExchange("/game/tm2-trial/maps?secret=arso")
    MapsResponseDto getTm2TrialMaps();

    /**
     * Get "TM2 RPG PVM" maps from tmrpg.com
     * @return
     */
    @GetExchange("/game/tm2/maps?secret=arso&mode=pvm")
    MapsResponseDto getTm2RpgPvmMaps();

}
