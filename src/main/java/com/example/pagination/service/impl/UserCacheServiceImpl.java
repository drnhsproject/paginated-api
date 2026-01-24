package com.example.pagination.service.impl;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.pagination.client.ExternalApiClient;
import com.example.pagination.model.User;
import com.example.pagination.service.UserCacheService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of UserCacheService with caching.
 * This service is responsible for caching all users from external API.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserCacheServiceImpl implements UserCacheService {

    private final ExternalApiClient externalApiClient;

    /**
     * Fetch all users from external API with caching.
     * Cache is managed by Spring Cache with TTL configured in application.yaml
     * 
     * IMPORTANT: This method is in a separate service to ensure Spring Cache proxy
     * works correctly.
     * Self-invocation within the same class would bypass the cache proxy.
     */
    @Cacheable(value = "users", unless = "#result == null")
    @Override
    public List<User> getAllUsers() {
        log.info("🔴 CACHE MISS - Fetching all users from external API");
        List<User> users = externalApiClient.fetchAllUsers();
        log.info("✅ Successfully fetched {} users from external API", users.size());
        return users;
    }
}
