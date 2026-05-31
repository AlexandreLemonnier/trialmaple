package com.trialmaple.model.enums;

import lombok.Getter;

@Getter
public enum GameMode {
    CLASSIC_TMNF_TRIAL(null),
    CLASSIC_TMNF_RPG(null),
    CLASSIC_TM2_TRIAL(null),
    CLASSIC_TM2_RPG(null),
    CLASSIC_TM2020_TRIAL(null),
    CLASSIC_TM2020_RPG(null),
    GEOGUESSR_TM2020_RPG("TM2020_RPG"),
    BLUR_TMNF_TRIAL("TMNF_TRIAL");

    private final String picturesFolderName;

    GameMode(String picturesFolderName) {
        this.picturesFolderName = picturesFolderName;
    }

}
