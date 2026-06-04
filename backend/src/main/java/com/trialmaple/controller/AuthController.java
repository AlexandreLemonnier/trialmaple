package com.trialmaple.controller;

import com.trialmaple.exception.DiscordAuthenticationException;
import com.trialmaple.exception.DiscordErrorCode;
import com.trialmaple.model.dto.UserDto;
import com.trialmaple.model.dto.auth.DiscordLoginRequestDto;
import com.trialmaple.model.dto.auth.LoginResponseDto;
import com.trialmaple.model.dto.external.discord.DiscordUserDto;
import com.trialmaple.service.auth.DiscordAuthService;
import com.trialmaple.utils.JwtUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    private final DiscordAuthService discordAuthService;
    private final JwtUtils jwtUtils;

    public AuthController(DiscordAuthService discordAuthService, JwtUtils jwtUtils) {
        this.discordAuthService = discordAuthService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/discord")
    public LoginResponseDto loginWithDiscord(@RequestBody DiscordLoginRequestDto request) {
        String code = request.code();

        if (code == null || code.isBlank()) {
            throw new DiscordAuthenticationException(DiscordErrorCode.INVALID_DISCORD_CODE, "Missing authorization code.");
        }

        DiscordUserDto discordUser = discordAuthService.authenticateWithDiscord(code);
        String discordId = discordUser.id();
        String username = discordUser.username();

        // TODO : Find user with discordId. If absent, create it.

        String jwt = jwtUtils.generateToken(discordId, username);

        UserDto userDto = new UserDto(username);
        return new LoginResponseDto(jwt, userDto);
    }
}
