package com.trialmaple.controller.mappers.external;

import com.trialmaple.model.dto.external.discord.DiscordUserDto;
import com.trialmaple.model.entities.User;
import org.springframework.stereotype.Component;

@Component
public class DiscordUserMapper {
    public User externalToService(DiscordUserDto discordUser) {
        return new User(
                Long.parseLong(discordUser.id()),
                discordUser.username(),
                discordUser.globalName(),
                discordUser.avatar(),
                discordUser.discriminator()
        );
    }
}
