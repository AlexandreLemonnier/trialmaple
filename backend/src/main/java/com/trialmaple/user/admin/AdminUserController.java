package com.trialmaple.user.admin;

import com.trialmaple.core.config.RouteKey;
import com.trialmaple.user.UserDto;
import com.trialmaple.user.UserMapper;
import com.trialmaple.user.UserService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(RouteKey.ADMIN_PREFIX + "/users")
@CrossOrigin(origins = "*")
public class AdminUserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public AdminUserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers().stream()
                .map(userMapper::serviceToDto)
                .toList();
    }
}