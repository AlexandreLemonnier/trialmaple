package com.trialmaple.core.config;

public final class RouteKey {
    private RouteKey() {}

    public static final String BASE_API = "/api";

    // ADMIN
    public static final String ADMIN_PREFIX = BASE_API + "/admin";

    // MAPS
    public static final String MAPS_PREFIX = BASE_API + "/maps";
    public static final String MAPS_LIST = "/list";
    public static final String GEOGUESSR_MAPS = "/geoguessr";
    public static final String BLUR_MAPS = "/blur";
    public static final String ZOOM_MAPS = "/zoom";

    // GUESS
    public static final String GUESS_PREFIX = BASE_API + "/guess";
    public static final String GIVE_UP = "/giveup";

    // DAILY MAP
    public static final String DAILY_MAP_PREFIX = BASE_API + "/daily-map";
    public static final String DAILY_MAP_UUID = "/uuid";

    // PICTURES
    public static final String GEOGUESSR_PICTURE = "/geoguessr-picture/{pictureNumber}";
    public static final String BLUR_PICTURE = "/blur-picture";
    public static final String ZOOM_PICTURE = "/zoom-picture";

    // USER
    public static final String USERS_PREFIX = BASE_API + "/users";
    public static final String CURRENT_USER = "/me";

    // STATS
    public static final String DAILY_STATS = "/daily-stats";
    public static final String USER_STATS = "/user-stats";

    // AUTH
    public static final String AUTH_PREFIX = BASE_API + "/auth";
    public static final String BACKOFFICE_AUTH_PREFIX = "/backoffice";
    public static final String DISCORD_AUTH = "/discord";
}
