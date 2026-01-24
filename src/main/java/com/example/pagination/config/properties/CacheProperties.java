package com.example.pagination.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "cache")
public class CacheProperties {
    /**
     * Time-to-live for cache entries in seconds.
     * Default is 600 (10 minutes).
     */
    private int ttlSeconds = 600;
}
