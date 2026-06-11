package com.trialmaple.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum GameMode {
    CLASSIC_TMNF_TRIAL(TmGame.TMNF, TmCategory.TRIAL, null),
    GEOGUESSR_TMNF_TRIAL(TmGame.TMNF, TmCategory.TRIAL, "TMNF_TRIAL"),
    BLUR_TMNF_TRIAL(TmGame.TMNF, TmCategory.TRIAL, "TMNF_TRIAL"),
    ZOOM_TMNF_TRIAL(TmGame.TMNF, TmCategory.TRIAL, "TMNF_TRIAL"),
    CLASSIC_TMNF_RPG(TmGame.TMNF, TmCategory.RPG, null),
    CLASSIC_TM2_TRIAL(TmGame.TM2, TmCategory.TRIAL, null),
    CLASSIC_TM2_RPG(TmGame.TM2, TmCategory.RPG, null),
    CLASSIC_TM2020_TRIAL(TmGame.TM2020, TmCategory.TRIAL, null),
    CLASSIC_TM2020_RPG(TmGame.TM2020, TmCategory.RPG, null),
    GEOGUESSR_TM2020_RPG(TmGame.TM2020, TmCategory.RPG, "TM2020_RPG");

    private final TmGame tmGame;
    private final TmCategory tmCategory;
    private final String picturesFolderName;

}
