package com.trialmaple.tmmap.admin;

import com.trialmaple.tmmap.DifficultyCategory;
import com.trialmaple.tmmap.tmuser.TmUserDto;

import java.util.List;

public record AdminTmMapDto(
        String uuid,
        Long tmxId,
        Boolean active,
        String name,
        String displayName,
        List<String> authors,
        Integer checkpointCount,
        DifficultyCategory difficulty,
        Integer points,
        String wrTime,
        Integer wrYear,
        TmUserDto wrHolder,
        Integer finisherCount,
        Integer releaseYear,
        Boolean classic
        ) {
        public AdminTmMapDto {
                if (active == null) active = false;
                if (classic == null) classic = false;
                if (checkpointCount == null) checkpointCount = 0;
                if (points == null) points = 0;
                if (finisherCount == null) finisherCount = 0;
        }
}
