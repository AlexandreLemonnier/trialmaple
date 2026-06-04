package com.trialmaple.model.dto.auth;

import com.trialmaple.model.dto.UserDto;

public record LoginResponseDto(
        String token,
        UserDto user
) {}
