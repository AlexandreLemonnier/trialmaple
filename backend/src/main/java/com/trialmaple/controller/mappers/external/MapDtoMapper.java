package com.trialmaple.controller.mappers.external;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Arrays;

import org.springframework.stereotype.Component;

import com.trialmaple.model.dto.external.tmrpg.MapDto;
import com.trialmaple.model.entities.TmMap;
import com.trialmaple.model.entities.TmUser;
import com.trialmaple.model.enums.MapList;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MapDtoMapper {

    public TmMap externalToService(MapDto map, MapList mapList, TmUser wrHolder) {
        return new TmMap(
            map.name(),
            Arrays.asList(map.author()),
            map.cps(),
            map.stars(),
            Duration.ofMillis(map.wrTime()),
            Instant.ofEpochSecond(map.wrDate()).atZone(ZoneOffset.UTC).getYear(),
            wrHolder,
            map.records(),
            Instant.ofEpochSecond(map.releaseDate()).atZone(ZoneOffset.UTC).getYear(),
            mapList);
    }

    /**
     * Update a TmMap with a new one
     * @param originalMap the map to update
     * @param updatedMap the new map
     * @param wrHolder the wrHolder entity
     * @return true if updated, false if there is nothing to update
     */
    public boolean update(TmMap originalMap, MapDto updatedMap, TmUser wrHolder) {
        boolean changed = false;
        String logFormat = "{} {}: {} -> {}";
        if (originalMap.getPoints() != updatedMap.stars()) {
            log.info(logFormat, originalMap.getName(), "points", originalMap.getPoints(), updatedMap.stars());
            originalMap.setPoints(updatedMap.stars());
            changed = true;
        }
        if (originalMap.getCheckpointCount() != updatedMap.cps()) {
            log.info(logFormat, originalMap.getName(), "checkpoints", originalMap.getCheckpointCount(), updatedMap.cps());
            originalMap.setCheckpointCount(updatedMap.cps());
            changed = true;
        }
        if (originalMap.getFinisherCount() != updatedMap.records()) {
            log.info(logFormat, originalMap.getName(), "finishers", originalMap.getFinisherCount(), updatedMap.records());
            originalMap.setFinisherCount(updatedMap.records());
            changed = true;
        }
        boolean hasWrHolderChanged = !originalMap.getWrHolder().getLogin().equals(wrHolder.getLogin());
        boolean hasWrTimeChanged = updatedMap.wrTime() != null && originalMap.getWrTime().toMillis() != updatedMap.wrTime();
        if (hasWrHolderChanged || hasWrTimeChanged) {
            log.info(logFormat, originalMap.getName(), "WR", originalMap.getWrTime(), Duration.ofMillis(updatedMap.wrTime()));
            originalMap.setWrTime(Duration.ofMillis(updatedMap.wrTime()));
            originalMap.setWrYear(Instant.ofEpochSecond(updatedMap.wrDate()).atZone(ZoneOffset.UTC).getYear());
            originalMap.setWrHolder(wrHolder);
            changed = true;
        }
        int updatedReleaseYear = Instant.ofEpochSecond(updatedMap.releaseDate()).atZone(ZoneOffset.UTC).getYear();
        if (originalMap.getReleaseYear() != updatedReleaseYear) {
            log.info(logFormat, originalMap.getName(), "release year", originalMap.getReleaseYear(), updatedReleaseYear);
            originalMap.setReleaseYear(updatedReleaseYear);
            changed = true;
        }
        return changed;
    }
}
