package com.trialmaple.score;


import com.trialmaple.core.config.CacheName;
import com.trialmaple.user.User;
import com.trialmaple.dailymap.DailyMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ScoreService {
    protected final ScoreRepository scoreRepository;

    public ScoreService(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    @Caching(evict = {
            @CacheEvict(value = CacheName.DAILY_STATS, key = "#dailyMap.gameMode"),
            @CacheEvict(value = CacheName.USER_STATS, key = "{#user.id, #dailyMap.gameMode}", condition = "#user != null")
    })
    public void saveScore(DailyMap dailyMap, int attemptCount, User user) {
        if (user != null && scoreRepository.findByDailyMapAndUser(dailyMap, user).isPresent()) {
            // A player can only have 1 score for each daily map
            log.warn("Player {} already has a score for daily map {} ({})", user.getUsername(), dailyMap.getMapName(), dailyMap.getId());
        } else {
            Score score = new Score(attemptCount, dailyMap, user);
            scoreRepository.save(score);
        }
    }
}
