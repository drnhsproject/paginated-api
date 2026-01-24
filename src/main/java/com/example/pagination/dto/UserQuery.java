package com.example.pagination.dto;

/**
 * Query object for user search and pagination.
 * Encapsulates all query parameters in a single, validated object.
 */
public record UserQuery(
        String name,
        String username,
        int page,
        int size) {

    /**
     * Compact constructor with validation.
     */
    public UserQuery {
        if (page <= 0) {
            throw new IllegalArgumentException("Page must be greater than 0");
        }
        if (size <= 0) {
            throw new IllegalArgumentException("Size must be greater than 0");
        }
    }

    /**
     * Factory method for creating query with defaults.
     */
    public static UserQuery of(String name, String username, Integer page, Integer size) {
        return new UserQuery(
                name,
                username,
                page != null ? page : 1,
                size != null ? size : 10);
    }

    /**
     * Check if name filter is active.
     */
    public boolean hasNameFilter() {
        return name != null && !name.trim().isEmpty();
    }

    /**
     * Check if username filter is active.
     */
    public boolean hasUsernameFilter() {
        return username != null && !username.trim().isEmpty();
    }

    /**
     * Get normalized name for filtering (trimmed and lowercase).
     */
    public String normalizedName() {
        return hasNameFilter() ? name.trim().toLowerCase() : null;
    }
}
