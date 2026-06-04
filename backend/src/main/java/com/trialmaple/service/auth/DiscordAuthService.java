package com.trialmaple.service.auth;

import com.trialmaple.exception.DiscordAuthenticationException;
import com.trialmaple.exception.DiscordErrorCode;
import com.trialmaple.model.dto.external.discord.DiscordTokenResponseDto;
import com.trialmaple.model.dto.external.discord.DiscordUserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class DiscordAuthService {

    private static final String TOKEN_ENDPOINT = "https://discord.com/api/oauth2/token";
    private static final String USER_ENDPOINT = "https://discord.com/api/users/@me";

    @Value("${discord.client-id}")
    private String clientId;

    @Value("${discord.client-secret}")
    private String clientSecret;

    @Value("${discord.redirect-uri}")
    private String redirectUri;

    private final RestTemplate restTemplate = new RestTemplate();

    public DiscordUserDto authenticateWithDiscord(String code) {
        String accessToken = getDiscordToken(code);
        return getUserInfo(accessToken);
    }

    /**
     * Exchange code to a Discord token
     * @param code the authorization code grant
     */
    private String getDiscordToken(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("grant_type", "authorization_code");
        body.add("code", code);
        body.add("redirect_uri", redirectUri);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        ResponseEntity<DiscordTokenResponseDto> tokenResponse = restTemplate.postForEntity(TOKEN_ENDPOINT, request, DiscordTokenResponseDto.class);
        DiscordTokenResponseDto responseBody = tokenResponse.getBody();
        if (responseBody == null || responseBody.accessToken() == null) {
            throw new DiscordAuthenticationException(DiscordErrorCode.DISCORD_API_ERROR, "Could not get access token from Discord for code " + code);
        }
        return responseBody.accessToken();
    }

    /**
     * Returns the user object of the requester’s account.
     * @param accessToken requester's access token
     */
    private DiscordUserDto getUserInfo(String accessToken) {
        HttpHeaders userHeaders = new HttpHeaders();
        userHeaders.setBearerAuth(accessToken);
        HttpEntity<String> userRequest = new HttpEntity<>(userHeaders);
        ResponseEntity<DiscordUserDto> userResponse = restTemplate.exchange(USER_ENDPOINT, HttpMethod.GET, userRequest, DiscordUserDto.class);
        DiscordUserDto responseBody = userResponse.getBody();
        if (responseBody == null) {
            throw new DiscordAuthenticationException(DiscordErrorCode.DISCORD_API_ERROR, "Could not get user details.");
        }
        return responseBody;
    }
}