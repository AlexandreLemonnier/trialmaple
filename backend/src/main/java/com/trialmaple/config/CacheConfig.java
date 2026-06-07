package com.trialmaple.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();

        CaffeineCache dailyStatsCache = new CaffeineCache(
                CacheName.DAILY_STATS,
                Caffeine.newBuilder().build()
        );

        CaffeineCache userStatsCache = new CaffeineCache(
                CacheName.USER_STATS,
                Caffeine.newBuilder()
                        .expireAfterWrite(5, TimeUnit.MINUTES)
                        .build()
        );

        cacheManager.setCaches(Arrays.asList(dailyStatsCache, userStatsCache));

        return cacheManager;
    }
}