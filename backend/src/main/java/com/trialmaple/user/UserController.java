package com.trialmaple.user;

import com.trialmaple.core.config.RouteKey;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping(RouteKey.USERS)
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping(RouteKey.CURRENT_USER)
    public UserDto getCurrentUser(Principal principal) {
        String discordId = principal.getName();
        User user = userService.findUserByDiscordId(discordId);
        return userMapper.serviceToDto(user);
    }
}
