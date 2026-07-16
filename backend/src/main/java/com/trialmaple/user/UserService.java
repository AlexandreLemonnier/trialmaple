package com.trialmaple.user;

import com.trialmaple.core.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    public User findUserByDiscordId(String discordId) throws UserNotFoundException {
        return userRepository.findByDiscordId(Long.valueOf(discordId))
                .orElseThrow(() -> new UserNotFoundException(discordId));
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User getUserFromPrincipal(Principal principal) throws UserNotFoundException {
        if (principal == null) {
            return null;
        }
        return findUserByDiscordId(principal.getName());
    }
}
