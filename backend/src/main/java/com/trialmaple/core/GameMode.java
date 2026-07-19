package com.trialmaple.core;

import com.trialmaple.tmmap.MapList;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum GameMode {
    CLASSIC_TMNF_TRIAL(TmGame.TMNF, TmCategory.TRIAL, MapList.TMNF_TRIAL_HARDEST, null),
    GEOGUESSR_TMNF_TRIAL(TmGame.TMNF, TmCategory.TRIAL, null, "TMNF_TRIAL"),
    BLUR_TMNF_TRIAL(TmGame.TMNF, TmCategory.TRIAL, null, "TMNF_TRIAL"),
    ZOOM_TMNF_TRIAL(TmGame.TMNF, TmCategory.TRIAL, null, "TMNF_TRIAL"),
    CLASSIC_TMNF_RPG(TmGame.TMNF, TmCategory.RPG, MapList.TMNF_RPG_CLASSIC, null),
    CLASSIC_TM2_TRIAL(TmGame.TM2, TmCategory.TRIAL, MapList.TM2_TRIAL, null),
    CLASSIC_TM2_RPG(TmGame.TM2, TmCategory.RPG, MapList.TM2_RPG_PVM, null),
    CLASSIC_TM2020_TRIAL(TmGame.TM2020, TmCategory.TRIAL, MapList.TM2020_TRIAL_CLASSIC, null),
    CLASSIC_TM2020_RPG(TmGame.TM2020, TmCategory.RPG, MapList.TM2020_RPG, null),
    GEOGUESSR_TM2020_RPG(TmGame.TM2020, TmCategory.RPG, null, "TM2020_RPG");

    private final TmGame tmGame;
    private final TmCategory tmCategory;
    private final MapList mapList;
    private final String picturesFolderName;

}
