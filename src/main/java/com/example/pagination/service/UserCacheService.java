package com.example.pagination.service;

import java.util.List;

import com.example.pagination.model.User;

/**
 * Service interface for cached user data operations.
 * Separates caching logic from pagination logic.
 */
public interface UserCacheService {

    /**
     * Fetch all users with caching.
     * This method is cached to avoid repeated calls to external API.
     * 
     * @return List of all users
     */
    List<User> getAllUsers();
}
