package com.trialmaple.tmmap.admin;

import com.trialmaple.core.GameMode;
import com.trialmaple.tmmap.MapList;
import com.trialmaple.tmmap.TmMap;
import com.trialmaple.tmmap.TmMapRepository;
import com.trialmaple.tmmap.tmuser.TmUser;
import com.trialmaple.tmmap.tmuser.TmUserDto;
import com.trialmaple.tmmap.tmuser.TmUserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminTmMapService {

    private final TmMapRepository tmMapRepository;
    private final TmUserRepository tmUserRepository;

    public List<TmMap> getFullMaps(GameMode gameMode) {
        MapList mapList = gameMode.getMapList();
        return mapList == null ? Collections.emptyList() : tmMapRepository.findByWrTimeIsNotNullAndMapList(mapList);
    }

    @Transactional
    public void updateMapsBatch(List<AdminTmMapDto> tmMapsDto) {
        for (AdminTmMapDto tmMapDto : tmMapsDto) {
            TmMap map = tmMapRepository.findByUuid(UUID.fromString(tmMapDto.uuid()))
                    .orElseThrow(() -> new EntityNotFoundException("Map not found for UUID: " + tmMapDto.uuid()));

            // Update basic fields
            map.setActive(tmMapDto.active());
            map.setName(tmMapDto.name());
            map.setDisplayName(tmMapDto.displayName());
            map.setAuthors(tmMapDto.authors());
            map.setDifficulty(tmMapDto.difficulty());
            map.setPoints(tmMapDto.points());
            map.setReleaseYear(tmMapDto.releaseYear());
            map.setFinisherCount(tmMapDto.finisherCount());

            // Convert time (String milliseconds -> Duration)
            if (tmMapDto.wrTime() != null && !tmMapDto.wrTime().isBlank()) {
                long millis = Long.parseLong(tmMapDto.wrTime());
                map.setWrTime(Duration.ofMillis(millis));
            } else {
                map.setWrTime(null);
            }

            // Handle WR Holder upsert
            if (tmMapDto.wrHolder() != null && tmMapDto.wrHolder().login() != null) {
                TmUserDto holderDto = tmMapDto.wrHolder();

                // Find player, if it doesn't exist, create it
                TmUser wrHolder = tmUserRepository.findByLoginAndGame(holderDto.login(), holderDto.game())
                        .orElseGet(() -> {
                            TmUser newUser = new TmUser(holderDto.login(), holderDto.displayName(), holderDto.game());
                            return tmUserRepository.save(newUser);
                        });

                map.setWrHolder(wrHolder);
            } else {
                map.setWrHolder(null);
            }
        }
    }
}