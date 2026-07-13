package com.trialmaple.user;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.trialmaple.core.exception.UserNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUserIfAbsent(User user) {
        Long discordId = user.getDiscordId();
        Optional<User> userBase = userRepository.findByDiscordId(discordId);
        if (userBase.isEmpty()) {
            return createUser(user);
        }

        return updateUser(userBase.get(), user);
    }

    private User createUser(User user) {
        log.info("Create user {}", user.getUsername());
        user.setCreationDate(LocalDate.now());
        return userRepository.save(user);
    }

    private User updateUser(User existingUser, User updatedUser) {
        log.info("Update user {} ({})", updatedUser.getUsername(), updatedUser.getGlobalName());
        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setGlobalName(updatedUser.getGlobalName());
        existingUser.setAvatar(updatedUser.getAvatar());
        existingUser.setDiscriminator(updatedUser.getDiscriminator());
        return userRepository.save(existingUser);
    }

    public User findUser(String discordId) throws UserNotFoundException {
        return userRepository.findByDiscordId(Long.valueOf(discordId))
                .orElseThrow(() -> new UserNotFoundException(discordId));
    }

    public User getUserFromPrincipal(Principal principal) throws UserNotFoundException {
        if (principal == null) {
            return null;
        }
        return findUser(principal.getName());
    }
}
