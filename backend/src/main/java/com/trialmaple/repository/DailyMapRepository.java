package com.trialmaple.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trialmaple.model.entities.DailyMap;

public interface DailyMapRepository extends JpaRepository<DailyMap, Long> {
    boolean existsByDay(LocalDateTime day);

    Optional<DailyMap> findByDay(LocalDateTime day);
}
