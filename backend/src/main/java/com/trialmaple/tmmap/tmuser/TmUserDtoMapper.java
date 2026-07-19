package com.trialmaple.tmmap.tmuser;

import org.springframework.stereotype.Component;

@Component
public class TmUserDtoMapper {
    public TmUserDto serviceToDto(TmUser tmUser) {
        return tmUser == null ? null : new TmUserDto(
                tmUser.getLogin(),
                tmUser.getDisplayName(),
                tmUser.getGame()
        );
    }
}
