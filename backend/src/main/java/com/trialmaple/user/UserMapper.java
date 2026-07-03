package com.trialmaple.user;

import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDto serviceToDto(User user) {
        return new UserDto(
               user.getDiscordId().toString(),
               user.getGlobalName() != null ? user.getGlobalName(): user.getUsername(),
               user.getAvatar(),
               user.getDiscriminator()
        );
    }
}
