package com.example.pagination.client.impl;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.pagination.client.ExternalApiClient;
import com.example.pagination.config.properties.ExternalApiProperties;
import com.example.pagination.dto.ExternalApiResponse;
import com.example.pagination.exception.ExternalApiException;
import com.example.pagination.model.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of ExternalApiClient using Spring's RestClient.
 * Fetches user data from DummyJSON API.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ExternalApiClientImpl implements ExternalApiClient {

    private final RestClient restClient;
    private final ExternalApiProperties properties;

    @Override
    public List<User> fetchAllUsers() {
        // Construct URL safely using UriComponentsBuilder
        // This handles slash concatenation and query parameters automatically
        String url = UriComponentsBuilder
                .fromUriString(properties.getBaseUrl())
                .path(properties.getUsersEndpoint())
                .queryParam("limit", 100)
                .toUriString();

        log.info("Fetching all users from external API: {}", url);

        try {
            ExternalApiResponse response = restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(ExternalApiResponse.class);

            if (response == null || response.users() == null) {
                log.error("Received null response from external API");
                throw new ExternalApiException("External API returned null response");
            }

            log.info("Successfully fetched {} users from external API", response.users().size());
            return response.users();

        } catch (RestClientException e) {
            log.error("Error fetching users from external API", e);
            throw new ExternalApiException("Failed to fetch users from external API: " + e.getMessage(), e);
        }
    }
}
