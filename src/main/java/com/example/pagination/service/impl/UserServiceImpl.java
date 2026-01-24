package com.example.pagination.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.pagination.dto.PaginationResponse;
import com.example.pagination.dto.UserQuery;
import com.example.pagination.model.User;
import com.example.pagination.service.UserCacheService;
import com.example.pagination.service.UserService;

import com.example.pagination.specification.UserSpecificationFactory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of UserService with pagination and filtering.
 * Uses UserCacheService for cached data access.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserCacheService userCacheService;

    @Override
    public PaginationResponse<User> getUsers(UserQuery query) {
        log.info("Fetching users - query: {}", query);

        // 1. Fetch all users (Cached)
        List<User> allUsers = userCacheService.getAllUsers();

        // 2. Filter Users (using Specification Pattern via Factory)
        List<User> filteredUsers = UserSpecificationFactory.filter(allUsers, query);
        int totalItems = filteredUsers.size();

        log.debug("Total users after filtering: {} (original: {})", totalItems, allUsers.size());

        // 3. Paginate
        int totalPages = (int) Math.ceil((double) totalItems / query.size());
        int startIndex = (query.page() - 1) * query.size();
        int endIndex = Math.min(startIndex + query.size(), totalItems);

        // Handle case where page exceeds total pages
        List<User> pageData;
        if (startIndex >= totalItems) {
            log.debug("Page {} exceeds total pages {}, returning empty data", query.page(), totalPages);
            pageData = Collections.emptyList();
        } else {
            pageData = filteredUsers.subList(startIndex, endIndex);
            log.debug("Successfully retrieved {} users for page {}", pageData.size(), query.page());
        }

        return new PaginationResponse<>(
                query.page(),
                query.size(),
                totalItems,
                totalPages,
                pageData);
    }
}
