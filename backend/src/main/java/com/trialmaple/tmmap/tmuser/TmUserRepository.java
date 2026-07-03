package com.trialmaple.tmmap.tmuser;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trialmaple.core.TmGame;

public interface TmUserRepository extends JpaRepository<TmUser, Long> {
    Optional<TmUser> findByLoginAndGame(String login, TmGame game);
}