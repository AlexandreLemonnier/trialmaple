package com.trialmaple.user;

import com.trialmaple.core.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Optional;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUserIfAbsent(User user) {
        Long discordId = user.getDiscordId();
        Optional<User> userBase = userRepository.findByDiscordId(discordId);
        if (userBase.isEmpty()) {
            createUser(user);
        } else {
            // Can be removed later
            updateUser(userBase.get(), user);
        }
    }

    private void createUser(User user) {
        log.info("Create user {}", user.getUsername());
        user.setCreationDate(LocalDate.now());
        userRepository.save(user);
    }

    private void updateUser(User existingUser, User updatedUser) {
        log.info("Update user {} ({})", updatedUser.getUsername(), updatedUser.getGlobalName());
        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setGlobalName(updatedUser.getGlobalName());
        userRepository.save(existingUser);
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
