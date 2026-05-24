package com.trialmaple.controller.mappers;

import com.trialmaple.model.dto.TmMapDto;
import com.trialmaple.model.entities.TmMap;
import org.springframework.stereotype.Component;

@Component
public class TmMapDtoMapper {
    public TmMapDto serviceToDto(TmMap tmMap) {
        return new TmMapDto(tmMap.getUuid().toString(), tmMap.getName(), tmMap.getDisplayName(), tmMap.getTmxId());
    }
}
