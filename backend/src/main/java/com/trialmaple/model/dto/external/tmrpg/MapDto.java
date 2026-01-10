package com.trialmaple.model.dto.external.tmrpg;

public record MapDto(
    Long id, 
    String uid, 
    String name, 
    int stars, 
    Long releaseDate, 
    String author, 
    int records, 
    Long wrTime, 
    Long wrDate, 
    int cps, 
    WrHolderDto wrHolder) {
        /**
         * Get a MapDto with fixed CP count.
         * tmrpg returns 1 extra CP because finish block is counted as one checkpoint
         * @return
        */
        public MapDto fixCpsCount() {
            return new MapDto(id, uid, name, stars, releaseDate, author, records, wrTime, wrDate, cps - 1, wrHolder);
        }
}

