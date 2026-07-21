package com.trialmaple.tmmap.admin;

import com.trialmaple.core.utils.TimeUtils;
import com.trialmaple.tmmap.MapList;
import com.trialmaple.tmmap.TmMap;
import com.trialmaple.tmmap.tmuser.TmUserDtoMapper;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class AdminTmMapDtoMapper {

    private final TmUserDtoMapper tmUserDtoMapper;

    public AdminTmMapDtoMapper(TmUserDtoMapper tmUserDtoMapper) {
        this.tmUserDtoMapper = tmUserDtoMapper;
    }

    public AdminTmMapDto serviceToDto(TmMap tmMap) {
        return tmMap == null ? null : new AdminTmMapDto(
                tmMap.getUuid().toString(),
                tmMap.getTmxId(),
                tmMap.isActive(),
                tmMap.getName(),
                tmMap.getDisplayName(),
                tmMap.getAuthors(),
                tmMap.getCheckpointCount(),
                tmMap.getDifficulty(),
                tmMap.getPoints(),
                TimeUtils.formatDuration(tmMap.getWrTime()),
                tmMap.getWrYear(),
                tmUserDtoMapper.serviceToDto(tmMap.getWrHolder()),
                tmMap.getFinisherCount(),
                tmMap.getReleaseYear(),
                tmMap.isClassic()
        );
    }

    public TmMap dtoToService(AdminTmMapDto tmMapDto, MapList mapList) {
        if (tmMapDto == null) {
            return null;
        }
        TmMap map = new TmMap(
                tmMapDto.tmxId(),
                tmMapDto.name(),
                tmMapDto.displayName(),
                tmMapDto.authors(),
                tmMapDto.checkpointCount(),
                tmMapDto.points(),
                Duration.ofMillis(Long.parseLong(tmMapDto.wrTime())),
                tmMapDto.wrYear(),
                tmUserDtoMapper.dtoToService(tmMapDto.wrHolder()),
                tmMapDto.finisherCount(),
                tmMapDto.releaseYear(),
                mapList,
                tmMapDto.classic()
        );
        map.setDifficulty(tmMapDto.difficulty());
        return map;
    }
}
