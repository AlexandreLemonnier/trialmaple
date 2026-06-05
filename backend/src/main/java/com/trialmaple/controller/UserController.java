package com.trialmaple.controller;

import com.trialmaple.controller.mappers.UserMapper;
import com.trialmaple.model.dto.UserDto;
import com.trialmaple.model.entities.User;
import com.trialmaple.service.user.UserService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("/me")
    public UserDto getCurrentUser(Principal principal) {
        String discordId = principal.getName();
        User user = userService.findUser(discordId);
        return userMapper.serviceToDto(user);
    }
}
