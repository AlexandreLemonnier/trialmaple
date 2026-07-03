package com.trialmaple.external.discord;

import com.trialmaple.user.User;
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
