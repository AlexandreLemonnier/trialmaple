package com.trialmaple.security.auth;

import com.trialmaple.user.UserDto;

public record LoginResponseDto(
        String token,
        UserDto user
) {}
