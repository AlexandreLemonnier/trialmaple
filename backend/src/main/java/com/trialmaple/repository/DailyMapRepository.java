package com.trialmaple.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.trialmaple.model.dto.projection.MapPickCount;
import com.trialmaple.model.entities.DailyMap;
import com.trialmaple.model.enums.GameMode;

public interface DailyMapRepository extends JpaRepository<DailyMap, Long> {
    boolean existsByDayAndGameMode(LocalDate day, GameMode gameMode);

    Optional<DailyMap> findByDayAndGameMode(LocalDate day, GameMode gameMode);

    long countByGameMode(GameMode gameMode);

    @Query("SELECT new com.trialmaple.model.dto.projection.MapPickCount(dailyMap.map.id, COUNT(dailyMap)) FROM DailyMap dailyMap WHERE dailyMap.map.id IN :mapPoolIds GROUP BY dailyMap.map.id")
    List<MapPickCount> countDailyMapsByMap(@Param("mapPoolIds") List<Long> mapPoolIds);
}
