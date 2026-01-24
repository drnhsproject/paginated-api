package com.example.pagination.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "external.api")
public class ExternalApiProperties {
    /**
     * Base URL of the external API.
     */
    private String baseUrl;

    /**
     * Endpoint for fetching users.
     */
    private String usersEndpoint;

    /**
     * Timeout for API calls in milliseconds.
     */
    private int timeout;
}
