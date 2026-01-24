package com.example.pagination.specification;

import java.util.ArrayList;
import java.util.List;

import com.example.pagination.dto.UserQuery;
import com.example.pagination.model.User;

/**
 * Factory to create a composite specification based on UserQuery.
 */
public class UserSpecificationFactory {

    /**
     * Build a combined specification from the query object.
     * 
     * @param query The user query containing filter parameters
     * @return A single UserSpecification combining all filters
     */
    public static UserSpecification build(UserQuery query) {
        List<UserSpecification> specs = new ArrayList<>();

        // 1. Filter by Name (if exists)
        if (query.hasNameFilter()) {
            specs.add(new NameSpecification(query.name()));
        }

        // 2. Filter by Username (if exists)
        if (query.hasUsernameFilter()) {
            specs.add(new UsernameSpecification(query.username()));
        }

        // Combine all specifications
        return user -> specs.stream().allMatch(spec -> spec.isSatisfiedBy(user));
    }

    /**
     * Filter a list of users based on the query.
     */
    public static List<User> filter(List<User> users, UserQuery query) {
        UserSpecification spec = build(query);
        return users.stream()
                .filter(spec::isSatisfiedBy)
                .toList();
    }
}
