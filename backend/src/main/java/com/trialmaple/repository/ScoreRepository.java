package com.trialmaple.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trialmaple.model.entities.DailyMap;
import com.trialmaple.model.entities.Score;

public interface ScoreRepository extends JpaRepository<Score, Long> {
    List<Score> findByDailyMap(DailyMap dailyMap);
}
