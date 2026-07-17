package com.trialmaple.tmmap.admin;

import com.trialmaple.core.TmGame;

public record TmUserDto(String login, String displayName, TmGame game) {
}
