package com.example.pagination.dto;

import java.util.List;

import com.example.pagination.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * DTO to map the response from DummyJSON API.
 * The API returns data nested under "users" key.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record ExternalApiResponse(
        List<User> users,
        int total,
        int skip,
        int limit) {
}
