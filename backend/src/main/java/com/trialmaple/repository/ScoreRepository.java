package com.trialmaple.repository;

import com.trialmaple.model.entities.Score;
import com.trialmaple.model.entities.User;
import com.trialmaple.model.entities.dailymap.DailyMap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScoreRepository extends JpaRepository<Score, Long> {
    List<Score> findByDailyMap(DailyMap dailyMap);
    Optional<Score> findByDailyMapAndUser(DailyMap dailyMap, User user);
}
