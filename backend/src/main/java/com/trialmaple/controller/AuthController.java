package com.trialmaple.controller;

import com.trialmaple.config.RouteKey;
import com.trialmaple.controller.mappers.UserMapper;
import com.trialmaple.controller.mappers.external.DiscordUserMapper;
import com.trialmaple.exception.DiscordAuthenticationException;
import com.trialmaple.exception.DiscordErrorCode;
import com.trialmaple.model.dto.UserDto;
import com.trialmaple.model.dto.auth.DiscordLoginRequestDto;
import com.trialmaple.model.dto.auth.LoginResponseDto;
import com.trialmaple.model.dto.external.discord.DiscordUserDto;
import com.trialmaple.model.entities.User;
import com.trialmaple.service.auth.DiscordAuthService;
import com.trialmaple.service.user.UserService;
import com.trialmaple.utils.JwtUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(RouteKey.AUTH_PREFIX)
@CrossOrigin(origins = "*")
public class AuthController {
    private final DiscordAuthService discordAuthService;
    private final JwtUtils jwtUtils;
    private final UserService userService;
    private final DiscordUserMapper discordUserMapper;
    private final UserMapper userMapper;

    public AuthController(
            DiscordAuthService discordAuthService,
            JwtUtils jwtUtils,
            UserService userService,
            DiscordUserMapper discordUserMapper,
            UserMapper userMapper
    ) {
        this.discordAuthService = discordAuthService;
        this.jwtUtils = jwtUtils;
        this.userService = userService;
        this.discordUserMapper = discordUserMapper;
        this.userMapper = userMapper;
    }

    @PostMapping(RouteKey.DISCORD_AUTH)
    public LoginResponseDto loginWithDiscord(@RequestBody DiscordLoginRequestDto request) {
        String code = request.code();

        if (code == null || code.isBlank()) {
            throw new DiscordAuthenticationException(DiscordErrorCode.INVALID_DISCORD_CODE, "Missing authorization code.");
        }

        DiscordUserDto discordUser = discordAuthService.authenticateWithDiscord(code);
        User user = discordUserMapper.externalToService(discordUser);

        // Save user if not existing yet
        userService.createUserIfAbsent(user);

        // Generate JWT
        UserDto userDto = userMapper.serviceToDto(user);
        String jwt = jwtUtils.generateToken(userDto.discordId(), userDto.username());

        return new LoginResponseDto(jwt, userDto);
    }
}
