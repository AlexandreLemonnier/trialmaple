package com.trialmaple.model.dto.external.tmrpg;

public record MapDto(
    Long id,
    String uid,
    String name,
    String displayName,
    int stars,
    Long releaseDate,
    String author,
    int records,
    Long wrTime,
    Long wrDate,
    int cps,
    WrHolderDto wrHolder) {
        /**
         * Get a MapDto with fixed CP count and without blank spaces on names/displayName.
         * tmrpg returns 1 extra CP because finish block is counted as one checkpoint
         * @return
        */
        public MapDto fixMap() {
            return new MapDto(id, uid, name.trim(), displayName.trim(), stars, releaseDate, author, records, wrTime, wrDate, cps - 1, wrHolder);
        }
}

