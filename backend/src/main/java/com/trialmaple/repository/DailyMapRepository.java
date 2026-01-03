package com.trialmaple.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trialmaple.model.entities.DailyMap;
import com.trialmaple.model.enums.GameMode;

public interface DailyMapRepository extends JpaRepository<DailyMap, Long> {
    boolean existsByDayAndGameMode(LocalDate day, GameMode gameMode);

    Optional<DailyMap> findByDayAndGameMode(LocalDate day, GameMode gameMode);

    long countByGameMode(GameMode gameMode);
}
