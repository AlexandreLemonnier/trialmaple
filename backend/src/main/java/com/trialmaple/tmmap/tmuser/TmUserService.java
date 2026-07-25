package com.trialmaple.tmmap.tmuser;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.trialmaple.core.TmGame;

import jakarta.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class TmUserService {
    private final TmUserRepository tmUserRepository;

    /**
     * Get the TM user corresponding to the given login if present, else create it with given parameters
     */
    @Transactional
    public TmUser getOrCreate(String login, String displayName, TmGame game) {
        return tmUserRepository.findByLoginAndGame(login, game)
            .orElseGet(() -> tmUserRepository.save(new TmUser(login, displayName, game)));
    }
}
