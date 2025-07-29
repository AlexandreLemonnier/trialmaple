package com.trialmaple.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trialmaple.model.TrialMap;

public interface TrialMapRepository extends JpaRepository<TrialMap, Long> {
    // Custom queries can be added here
}