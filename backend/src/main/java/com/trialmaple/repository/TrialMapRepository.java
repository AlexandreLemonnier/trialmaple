package com.trialmaple.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.trialmaple.model.entities.TrialMap;

public interface TrialMapRepository extends JpaRepository<TrialMap, Long> {
    Optional<TrialMap> findByNameIgnoreCase(String name);

    List<TrialMap> findByWorldRecordIsNotNull();

    @Query("SELECT map.name FROM TrialMap map WHERE (:finished = false OR map.worldRecord IS NOT NULL)")
    List<String> findAllMapNames(@Param("finished") boolean finished);
}