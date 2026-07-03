package com.trialmaple.external.tmrpg;

import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

/**
 * tmrpg.com is run by Achepta
 */
@HttpExchange(url = "https://tmrpg.com/api", accept = "application/json")
public interface TmRpgService {

    /**
     * Get "TMNF Classic RPG" maps from tmrpg.com
     */
    @GetExchange("/game/tmnf/maps?secret=arso&mode=classic")
    MapsResponseDto getTmnfRpgClassicMaps();

    /**
     * Get "TM2 Trial" maps from tmrpg.com
     */
    @GetExchange("/game/tm2-trial/maps?secret=arso")
    MapsResponseDto getTm2TrialMaps();

    /**
     * Get "TM2 RPG PVM" maps from tmrpg.com
     */
    @GetExchange("/game/tm2/maps?secret=arso&mode=pvm")
    MapsResponseDto getTm2RpgPvmMaps();

    /**
     * Get "TM2 RPG Classic" maps from tmrpg.com
     */
    @GetExchange("/game/tm2/maps?secret=arso&mode=classic")
    MapsResponseDto getTm2RpgClassicMaps();

    /**
     * Get "TM2020 Trial Classic" maps from tmrpg.com
     */
    @GetExchange("/game/tm20-trial/maps?secret=arso&mode=classic")
    MapsResponseDto getTm2020TrialClassicMaps();

    /**
     * Get "TM2020 RPG" maps from tmrpg.com
     */
    @GetExchange("/game/tm20/maps?secret=arso")
    MapsResponseDto getTm2020RpgMaps();

}
