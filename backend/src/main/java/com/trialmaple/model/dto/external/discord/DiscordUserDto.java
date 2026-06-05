package com.trialmaple.model.dto.external.discord;

public record DiscordUserDto(
        String id,
        String username,
        String avatar,
        String discriminator) {
}
