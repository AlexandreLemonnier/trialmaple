package com.trialmaple.controller.mappers.external;

import ch.qos.logback.core.util.StringUtil;
import com.trialmaple.model.dto.external.discord.DiscordUserDto;
import com.trialmaple.model.entities.User;
import org.springframework.stereotype.Component;

@Component
public class DiscordUserMapper {
    public User externalToService(DiscordUserDto discordUser) {
        String name = StringUtil.isNullOrEmpty(discordUser.globalName()) ? discordUser.username() : discordUser.globalName();
        return new User(
                Long.parseLong(discordUser.id()),
                name,
                discordUser.avatar(),
                discordUser.discriminator()
        );
    }
}
