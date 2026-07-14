package com.trialmaple.security;

import com.trialmaple.user.UserType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
@Slf4j
public class JwtUtils {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpirationMs;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    public String generateToken(String discordId, String username, UserType userType) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .subject(discordId)
                .claim("username", username)
                .claim("userType", userType.name())
                .issuedAt(now)
                .expiration(expirationDate)
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * Extract discordId from token subject
     */
    public String getDiscordIdFromToken(String token) {
        return getClaims(token).getSubject();
    }

    public UserType getUserTypeFromToken(String token) {
        String userType = getClaims(token).get("userType", String.class);

        if (userType == null) {
            return UserType.USER;
        }

        try {
            return UserType.valueOf(userType);
        } catch (IllegalArgumentException e) {
            return UserType.USER;
        }
    }

    /**
     * Check if the token is valid
     */
    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.error("Invalid token", e);
            return false;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
