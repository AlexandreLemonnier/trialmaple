package com.trialmaple.security.auth;

public record AdminLoginRequestDto(
        String code,
        String redirectUri
) {}
