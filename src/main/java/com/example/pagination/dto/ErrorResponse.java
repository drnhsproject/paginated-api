package com.example.pagination.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Standard error response format.
 * Using Java 21 Record for immutability.
 */
public record ErrorResponse(
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'") LocalDateTime timestamp,
        int status,
        String error,
        String message) {
}
