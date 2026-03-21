package com.trialmaple.repository;

import com.trialmaple.model.entities.TmMap;
import com.trialmaple.model.enums.MapList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TmMapRepository extends JpaRepository<TmMap, Long> {
    Optional<TmMap> findByUuid(UUID uuid);

    List<TmMap> findByWrTimeIsNotNullAndActiveTrueAndMapList(MapList mapList);

    @Query("SELECT map.name FROM TmMap map WHERE (:finished = false OR map.wrTime IS NOT NULL)")
    List<String> findAllMapNames(@Param("finished") boolean finished);

    List<TmMap> findAllByTmxIdInAndMapList(Collection<Long> mapsIds, MapList mapList);
}