package com.trialmaple.user.admin;

import com.trialmaple.core.config.RouteKey;
import com.trialmaple.user.User;
import com.trialmaple.user.UserDto;
import com.trialmaple.user.UserMapper;
import com.trialmaple.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(RouteKey.ADMIN_PREFIX + RouteKey.USERS_PREFIX)
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AdminUserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers().stream()
                .map(userMapper::serviceToDto)
                .toList();
    }

    @GetMapping("/{discordId}")
    public UserDto getUser(@PathVariable String discordId) {
        User user = userService.findUserByDiscordId(discordId);
        return userMapper.serviceToDto(user);
    }
}