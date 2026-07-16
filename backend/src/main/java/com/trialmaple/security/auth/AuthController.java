package com.trialmaple.security.auth;

import com.trialmaple.core.exception.BackofficeAccessDeniedException;
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
import com.trialmaple.user.UserType;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

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
        User user = authenticateDiscordUser(request.code(), null);
        return createLoginResponse(user);
    }

    @PostMapping(RouteKey.BACKOFFICE_AUTH_PREFIX + RouteKey.DISCORD_AUTH)
    public LoginResponseDto loginAdminWithDiscord(@RequestBody AdminLoginRequestDto request) {
        String redirectUri = request.redirectUri();
        if (redirectUri == null || redirectUri.isBlank()) {
            throw new DiscordAuthenticationException(DiscordErrorCode.INVALID_DISCORD_CODE, "Missing redirect URI.");
        }

        User user = authenticateDiscordUser(request.code(), redirectUri);
        requireAdmin(user);

        return createLoginResponse(user);
    }

    @GetMapping(RouteKey.BACKOFFICE_AUTH_PREFIX + RouteKey.CURRENT_USER)
    public UserDto getCurrentAdminUser(Principal principal) {
        User user = userService.getUserFromPrincipal(principal);
        requireAdmin(user);
        return userMapper.serviceToDto(user);
    }

    private User authenticateDiscordUser(String code, String redirectUri) {
        if (code == null || code.isBlank()) {
            throw new DiscordAuthenticationException(DiscordErrorCode.INVALID_DISCORD_CODE, "Missing authorization code.");
        }

        DiscordUserDto discordUser = redirectUri == null
                ? discordAuthService.authenticateWithDiscord(code)
                : discordAuthService.authenticateWithDiscord(code, redirectUri);
        User user = discordUserMapper.externalToService(discordUser);

        // Save user if not existing yet
        return userService.createUserIfAbsent(user);
    }

    private LoginResponseDto createLoginResponse(User user) {
        // Generate JWT
        UserDto userDto = userMapper.serviceToDto(user);
        String jwt = jwtUtils.generateToken(userDto.discordId(), userDto.username(), userDto.userType());

        return new LoginResponseDto(jwt, userDto);
    }

    private void requireAdmin(User user) {
        if (user == null || user.getUserType() != UserType.ADMIN) {
            throw new BackofficeAccessDeniedException();
        }
    }
}
