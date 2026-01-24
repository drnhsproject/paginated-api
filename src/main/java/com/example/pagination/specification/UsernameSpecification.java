package com.example.pagination.specification;

import com.example.pagination.model.User;

/**
 * Specification for filtering users by username.
 * Case-insensitive partial match.
 */
public class UsernameSpecification implements UserSpecification {

    private final String searchTerm;

    public UsernameSpecification(String username) {
        this.searchTerm = (username != null) ? username.trim().toLowerCase() : null;
    }

    @Override
    public boolean isSatisfiedBy(User user) {
        // If search term is null/empty, specification is ignored (or always true)
        if (searchTerm == null || searchTerm.isEmpty()) {
            return true;
        }

        String username = user.username() != null ? user.username().toLowerCase() : "";

        return username.contains(searchTerm);
    }
}
