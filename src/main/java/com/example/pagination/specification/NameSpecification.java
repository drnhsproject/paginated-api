package com.example.pagination.specification;

import com.example.pagination.model.User;

/**
 * Specification for filtering users by name (firstName or lastName).
 * Case-insensitive partial match.
 */
public class NameSpecification implements UserSpecification {

    private final String searchTerm;

    public NameSpecification(String name) {
        this.searchTerm = (name != null) ? name.trim().toLowerCase() : null;
    }

    @Override
    public boolean isSatisfiedBy(User user) {
        // If search term is null/empty, specification is ignored (or always true)
        if (searchTerm == null || searchTerm.isEmpty()) {
            return true;
        }

        String firstName = user.firstName() != null ? user.firstName().toLowerCase() : "";
        String lastName = user.lastName() != null ? user.lastName().toLowerCase() : "";

        return firstName.contains(searchTerm) || lastName.contains(searchTerm);
    }
}
