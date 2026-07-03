package com.trialmaple.user;

public record UserDto(
        String discordId,
        String username,
        String avatar,
        String discriminator
) {}