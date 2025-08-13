package com.trialmaple.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trialmaple.model.entities.TrialMap;

public interface TrialMapRepository extends JpaRepository<TrialMap, Long> {
    Optional<TrialMap> findByNameIgnoreCase(String name);
}