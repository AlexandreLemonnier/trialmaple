package com.trialmaple.service.maps;

import org.springframework.stereotype.Service;

import com.trialmaple.model.entities.TmUser;
import com.trialmaple.model.enums.TmGame;
import com.trialmaple.repository.TmUserRepository;

import jakarta.transaction.Transactional;

@Service
public class TmUserService {
    private final TmUserRepository tmUserRepository;

    public TmUserService(TmUserRepository tmUserRepository) {
        this.tmUserRepository = tmUserRepository;
    }

    @Transactional
    public TmUser getOrCreate(String login, String displayName, TmGame game) {
        return tmUserRepository.findByLoginAndGame(login, game)
            .orElseGet(() -> tmUserRepository.save(new TmUser(login, displayName, game)));
    }
}
