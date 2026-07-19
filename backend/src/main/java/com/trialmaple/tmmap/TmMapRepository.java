package com.trialmaple.tmmap;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TmMapRepository extends JpaRepository<TmMap, Long> {
    Optional<TmMap> findByUuid(UUID uuid);

    List<TmMap> findByWrTimeIsNotNullAndActiveTrueAndMapList(MapList mapList);

    List<TmMap> findAllByTmxIdInAndMapList(Collection<Long> mapsIds, MapList mapList);

    List<TmMap> findByWrTimeIsNotNullAndMapList(MapList mapList);
}