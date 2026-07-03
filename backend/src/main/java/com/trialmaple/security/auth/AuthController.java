package com.trialmaple.security.auth;

import com.trialmaple.core.config.RouteKey;
import com.trialmaple.external.discord.DiscordAuthService;
import com.trialmaple.security.JwtUtils;
import com.trialmaple.user.UserMapper;
import com.trialmaple.external.discord.DiscordUserMapper;
import com.trialmaple.core.exception.DiscordAuthenticationException;
import com.trialmaple.core.exception.DiscordErrorCode;
import com.trialmaple.user.UserDto;
import com.trialmaple.external.discord.DiscordUserDto;
import com.trialmaple.user.User;
import com.trialmaple.user.UserService;
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
