package com.trialmaple.externalservice.tmrpg;

import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import com.trialmaple.model.dto.external.tmrpg.MapsResponseDto;

/**
 * tmrpg.com is run by Achepta
 */
@HttpExchange(url = "https://tmrpg.com/api", accept = "application/json")
public interface TmRpgService {

    @GetExchange("/game/tmnf/maps?secret=arso&mode=classic")
    MapsResponseDto getTmnfRpgClassicMaps();

}
