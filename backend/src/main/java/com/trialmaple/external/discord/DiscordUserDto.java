package com.trialmaple.external.discord;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DiscordUserDto(
        String id,
        String username,
        @JsonProperty("global_name") String globalName,
        String avatar,
        String discriminator) {
}
