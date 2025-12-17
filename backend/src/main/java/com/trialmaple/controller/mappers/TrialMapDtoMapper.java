package com.trialmaple.controller.mappers;

import org.springframework.stereotype.Component;

import com.trialmaple.model.dto.TrialMapDto;
import com.trialmaple.model.entities.TrialMap;
import com.trialmaple.utils.TimeUtils;

@Component
public class TrialMapDtoMapper {
    public TrialMapDto serviceToDto(TrialMap trialMap) {
        return new TrialMapDto(
                trialMap.getName(),
                trialMap.getAuthors(),
                trialMap.getCheckpointCount(),
                trialMap.getDifficulty().name(),
                trialMap.getPoints(),
                trialMap.getFinisherCount(),
                TimeUtils.formatDuration(trialMap.getWorldRecord()));
    }
}
