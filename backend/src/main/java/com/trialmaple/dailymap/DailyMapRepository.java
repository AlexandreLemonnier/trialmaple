package com.trialmaple.dailymap;

import com.trialmaple.dailymap.projection.MapPickCount;
import com.trialmaple.dailymap.projection.PictureUseCount;
import com.trialmaple.tmmap.TmMap;
import com.trialmaple.core.GameMode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DailyMapRepository extends JpaRepository<DailyMap, Long> {
    boolean existsByDayAndGameMode(LocalDate day, GameMode gameMode);

    Optional<DailyMap> findByDayAndGameMode(LocalDate day, GameMode gameMode);

    long countByGameMode(GameMode gameMode);

    @Query("SELECT DISTINCT dailyMap.map FROM DailyMap dailyMap WHERE dailyMap.gameMode = :gameMode AND dailyMap.day >= :startDate")
    List<TmMap> findTmMapsByGameModeAndStartDate(@Param("gameMode") GameMode gameMode, @Param("startDate") LocalDate startDate);

    @Query("SELECT DISTINCT dailyMap.dailyPictures.mapName FROM DailyMap dailyMap WHERE dailyMap.gameMode = :gameMode AND dailyMap.day >= :startDate")
    List<String> findGeoguessrMapNameByGameModeAndStartDate(@Param("gameMode") GameMode gameMode, @Param("startDate") LocalDate startDate);

    @Query("SELECT new com.trialmaple.model.dto.projection.MapPickCount(dailyMap.map.id, COUNT(dailyMap)) FROM DailyMap dailyMap WHERE dailyMap.map.id IN :mapPoolIds AND dailyMap.day >= :startDate GROUP BY dailyMap.map.id")
    List<MapPickCount> countDailyMapsByMapAndStartDate(@Param("mapPoolIds") List<Long> mapPoolIds, @Param("startDate") LocalDate startDate);

    int countByGameModeAndDayGreaterThanEqual(GameMode gameMode, LocalDate day);

    @Query("SELECT dailyPictures.mapName, picture, COUNT(dailyMap) " +
            "FROM DailyMap dailyMap " +
            "JOIN dailyMap.dailyPictures dailyPictures " +
            "JOIN dailyPictures.picturesName picture " +
            "WHERE dailyMap.gameMode = :gameMode " +
            "GROUP BY dailyPictures.mapName, INDEX(picture), picture")
    List<PictureUseCount> countPicturesUsageByGameMode(@Param("gameMode") GameMode gameMode);
}
