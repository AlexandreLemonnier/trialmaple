package com.trialmaple.tmmap.admin;

import com.trialmaple.tmmap.DifficultyCategory;
import com.trialmaple.tmmap.tmuser.TmUserDto;

import java.util.List;

public record AdminTmMapDto(
        String uuid,
        Long tmxId,
        boolean active,
        String name,
        String displayName,
        List<String> authors,
        int checkpointCount,
        DifficultyCategory difficulty,
        int points,
        String wrTime,
        Integer wrYear,
        TmUserDto wrHolder,
        int finisherCount,
        int releaseYear,
        boolean classic
        ) {}
