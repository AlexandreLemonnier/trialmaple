package com.trialmaple.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.trialmaple.model.entities.TmMap;
import com.trialmaple.model.enums.MapList;

public interface TmMapRepository extends JpaRepository<TmMap, Long> {
    Optional<TmMap> findByNameIgnoreCase(String name);

    List<TmMap> findByWrTimeIsNotNullAndActiveTrueAndMapList(MapList mapList);

    @Query("SELECT map.name FROM TmMap map WHERE (:finished = false OR map.wrTime IS NOT NULL)")
    List<String> findAllMapNames(@Param("finished") boolean finished);

    List<TmMap> findAllByNameInAndMapList(Collection<String> mapNames, MapList mapList);
}