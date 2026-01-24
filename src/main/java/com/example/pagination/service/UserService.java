package com.example.pagination.service;

import com.example.pagination.dto.PaginationResponse;
import com.example.pagination.dto.UserQuery;
import com.example.pagination.model.User;

/**
 * Service interface for user operations.
 * Follows Dependency Inversion Principle.
 */
public interface UserService {

    /**
     * Get paginated users based on query parameters.
     * 
     * @param query Query object containing pagination and filter parameters
     * @return Paginated response containing users
     * @throws IllegalArgumentException if query parameters are invalid
     */
    PaginationResponse<User> getUsers(UserQuery query);
}
