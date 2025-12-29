package com.trialmaple.controller.mappers;

import org.springframework.stereotype.Component;

import com.trialmaple.model.dto.TmMapDto;
import com.trialmaple.model.entities.TmMap;
import com.trialmaple.utils.TimeUtils;

@Component
public class TmMapDtoMapper {
    public TmMapDto serviceToDto(TmMap tmMap) {
        return new TmMapDto(
                tmMap.getName(),
                tmMap.getAuthors(),
                tmMap.getCheckpointCount(),
                tmMap.getDifficulty().name(),
                tmMap.getPoints(),
                tmMap.getFinisherCount(),
                TimeUtils.formatDuration(tmMap.getWrTime()),
                tmMap.getReleaseYear());
    }
}
