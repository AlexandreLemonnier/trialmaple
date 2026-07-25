package com.trialmaple.security.config;

import com.trialmaple.core.config.RouteKey;
import com.trialmaple.user.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {   

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable) // Disabled because Stateless REST API (JWT)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, RouteKey.AUTH_PREFIX + RouteKey.BACKOFFICE_AUTH_PREFIX + RouteKey.DISCORD_AUTH).permitAll()
                        .requestMatchers(RouteKey.AUTH_PREFIX + RouteKey.BACKOFFICE_AUTH_PREFIX + RouteKey.CURRENT_USER).hasRole(UserType.ADMIN.name())
                        .requestMatchers(RouteKey.ADMIN_PREFIX + "/**").hasRole(UserType.ADMIN.name())
                        .requestMatchers(RouteKey.USERS + RouteKey.CURRENT_USER).authenticated()
                        .anyRequest().permitAll()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
