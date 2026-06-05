package com.trialmaple.model.dto;

public record UserDto(
        String discordId,
        String username,
        String avatar,
        String discriminator
) {}