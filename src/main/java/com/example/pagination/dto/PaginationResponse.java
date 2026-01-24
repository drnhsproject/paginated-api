package com.example.pagination.dto;

import java.util.List;

/**
 * Generic pagination response wrapper.
 * Using Java 21 Record for immutability and clean API.
 * 
 * @param <T> Type of data being paginated
 */
public record PaginationResponse<T>(
        int page,
        int size,
        long totalItems,
        int totalPages,
        List<T> data) {
}
