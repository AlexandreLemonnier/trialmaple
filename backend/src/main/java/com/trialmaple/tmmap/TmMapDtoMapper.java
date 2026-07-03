package com.trialmaple.tmmap;

import com.trialmaple.tmmap.dto.TmMapDto;
import org.springframework.stereotype.Component;

@Component
public class TmMapDtoMapper {
    public TmMapDto serviceToDto(TmMap tmMap) {
        return new TmMapDto(tmMap.getUuid().toString(), tmMap.getName(), tmMap.getDisplayName(), tmMap.getTmxId());
    }
}
