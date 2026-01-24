package com.example.pagination.config;

import java.util.concurrent.TimeUnit;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.pagination.config.properties.CacheProperties;
import com.github.benmanes.caffeine.cache.Caffeine;

import lombok.RequiredArgsConstructor;

/**
 * Cache configuration using Caffeine.
 * Enables caching for external API responses with TTL.
 */
@Configuration
@EnableCaching
@RequiredArgsConstructor
public class CacheConfig {

    private final CacheProperties properties;

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("users");
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .expireAfterWrite(properties.getTtlSeconds(), TimeUnit.SECONDS)
                .maximumSize(100)
                .recordStats());
        return cacheManager;
    }
}
