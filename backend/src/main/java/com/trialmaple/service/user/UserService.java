package com.trialmaple.service.user;

import com.trialmaple.exception.UserNotFoundException;
import com.trialmaple.model.entities.User;
import com.trialmaple.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        log.info("Create user {}", user.getUsername());
        Long discordId = user.getDiscordId();
        Optional<User> userBase = userRepository.findByDiscordId(discordId);
        return userBase.orElseGet(() -> userRepository.save(user));
    }

    public User findUser(String discordId) throws UserNotFoundException {
        return userRepository.findByDiscordId(Long.valueOf(discordId))
                .orElseThrow(() -> new UserNotFoundException(discordId));
    }
}
