package com.example.pagination.client;

import java.util.List;

import com.example.pagination.model.User;

/**
 * Interface for external API client.
 * Follows Dependency Inversion Principle - depend on abstractions.
 */
public interface ExternalApiClient {

    /**
     * Fetch all users from the external API.
     * 
     * @return List of all users
     * @throws com.example.pagination.exception.ExternalApiException if API call
     *                                                               fails
     *
     */
    List<User> fetchAllUsers();
}
