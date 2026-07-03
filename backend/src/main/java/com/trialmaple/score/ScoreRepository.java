package com.trialmaple.score;

import com.trialmaple.user.User;
import com.trialmaple.dailymap.DailyMap;
import com.trialmaple.core.GameMode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ScoreRepository extends JpaRepository<Score, Long> {
    List<Score> findByDailyMap(DailyMap dailyMap);

    Optional<Score> findByDailyMapAndUser(DailyMap dailyMap, User user);

    /**
     * Count scores number for a given user and game mode, after a given date
     */
    int countByUserAndDailyMap_GameModeAndDailyMap_DayGreaterThanEqual(User user, GameMode gameMode, LocalDate day);

    /**
     * Count average attempts for a given user and game mode, after a given date
     */
    @Query("SELECT AVG(s.attemptCount) FROM Score s WHERE s.user = :user AND s.dailyMap.gameMode = :gameMode AND s.dailyMap.day >= :startDate")
    Double getAverageAttemptCount(@Param("user") User user, @Param("gameMode") GameMode gameMode, @Param("startDate") LocalDate startDate);
}