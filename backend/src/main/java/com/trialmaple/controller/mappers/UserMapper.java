package com.trialmaple.controller.mappers;

import com.trialmaple.model.dto.UserDto;
import com.trialmaple.model.entities.User;
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
