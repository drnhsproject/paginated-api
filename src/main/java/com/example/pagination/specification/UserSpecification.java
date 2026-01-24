package com.example.pagination.specification;

import com.example.pagination.model.User;

/**
 * Functional interface for user specifications.
 * Defines a condition that a user must satisfy.
 */
@FunctionalInterface
public interface UserSpecification {
    /**
     * Check if the user satisfies the specification.
     * 
     * @param user The user to check
     * @return true if satisfied, false otherwise
     */
    boolean isSatisfiedBy(User user);
    
    /**
     * Combine with another specification (AND logic).
     */
    default UserSpecification and(UserSpecification other) {
        return user -> this.isSatisfiedBy(user) && other.isSatisfiedBy(user);
    }
}
