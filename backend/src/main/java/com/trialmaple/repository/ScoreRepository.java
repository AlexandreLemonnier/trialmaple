package com.trialmaple.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trialmaple.model.entities.Score;

public interface ScoreRepository extends JpaRepository<Score, Long> {

}
