package com.trialmaple.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trialmaple.model.entities.TmUser;
import com.trialmaple.model.enums.TmGame;

public interface TmUserRepository extends JpaRepository<TmUser, Long> {
    Optional<TmUser> findByLoginAndGame(String login, TmGame game);
}