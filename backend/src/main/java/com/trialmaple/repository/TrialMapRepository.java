package com.trialmaple.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.trialmaple.model.entities.TrialMap;

public interface TrialMapRepository extends JpaRepository<TrialMap, Long> {
    Optional<TrialMap> findByNameIgnoreCase(String name);

    @Query("SELECT name FROM TrialMap")
    List<String> findAllMapNames();
}