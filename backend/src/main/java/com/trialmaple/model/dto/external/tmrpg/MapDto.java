package com.trialmaple.model.dto.external.tmrpg;

public record MapDto(Long id, String uid, String name, int stars, Long releaseDate, String author, int records, Long wrTime, Long wrDate, int cps, WrHolderDto wrHolder) {
}

