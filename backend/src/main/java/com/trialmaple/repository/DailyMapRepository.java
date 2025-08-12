package com.trialmaple.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trialmaple.model.entities.DailyMap;

public interface DailyMapRepository extends JpaRepository<DailyMap, Long> {
    boolean existsByDayBetween(LocalDate start, LocalDate end);
}
