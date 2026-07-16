export type ClassicGameMode = 'CLASSIC_TMNF_TRIAL'
    | 'CLASSIC_TMNF_RPG'
    | 'CLASSIC_TM2_TRIAL'
    | 'CLASSIC_TM2_RPG'
    | 'CLASSIC_TM2020_TRIAL'
    | 'CLASSIC_TM2020_RPG';

export type GeoguessrGameMode = 'GEOGUESSR_TMNF_TRIAL' | 'GEOGUESSR_TM2020_RPG';

export type BlurGameMode = 'BLUR_TMNF_TRIAL';

export type ZoomGameMode = 'ZOOM_TMNF_TRIAL';

export type GameMode = ClassicGameMode | GeoguessrGameMode | BlurGameMode | ZoomGameMode;

export const GAME_MODE_DISPLAY_NAMES: Record<GameMode, string> = {
    'CLASSIC_TMNF_TRIAL': 'TMNF Trial Classic',
    'CLASSIC_TMNF_RPG': 'TMNF RPG Classic',
    'CLASSIC_TM2_TRIAL': 'TM2 Trial Classic',
    'CLASSIC_TM2_RPG': 'TM2 RPG Classic',
    'CLASSIC_TM2020_TRIAL': 'TM2020 Trial Classic',
    'CLASSIC_TM2020_RPG': 'TM2020 RPG Classic',
    'GEOGUESSR_TMNF_TRIAL': 'TMNF Trial Geoguessr',
    'GEOGUESSR_TM2020_RPG': 'TM2020 RPG Geoguessr',
    'BLUR_TMNF_TRIAL': 'TMNF Trial Blurred',
    'ZOOM_TMNF_TRIAL': 'TMNF Trial Zoomed'
};
