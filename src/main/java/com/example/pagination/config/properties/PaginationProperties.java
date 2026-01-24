package com.example.pagination.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "pagination")
public class PaginationProperties {
    /**
     * Default page number (starts from 1).
     */
    private int defaultPage = 1;

    /**
     * Default page size.
     */
    private int defaultSize = 10;

    /**
     * Maximum allowed page size.
     */
    private int maxSize = 100;
}
