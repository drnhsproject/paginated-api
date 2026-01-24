package com.example.pagination.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.pagination.dto.PaginationResponse;
import com.example.pagination.dto.UserQuery;
import com.example.pagination.model.User;
import com.example.pagination.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * REST Controller for user-related endpoints.
 * Provides paginated user listing with optional filtering.
 */
@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "User management API")
public class UserController {

        private final UserService userService;

        /**
         * Get paginated list of users with optional name filtering.
         * 
         * @param name Optional name filter (searches in firstName and lastName)
         * @param page Page number (default: 1, min: 1)
         * @param size Number of items per page (default: 10, min: 1)
         * @return Paginated response containing users
         */
        @Operation(summary = "Get paginated users", description = "Retrieves a paginated list of users from the external API with optional name filtering")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Successfully retrieved users", content = @Content(schema = @Schema(implementation = PaginationResponse.class))),
                        @ApiResponse(responseCode = "400", description = "Invalid pagination parameters"),
                        @ApiResponse(responseCode = "500", description = "External API error or internal server error")
        })
        @GetMapping
        public ResponseEntity<PaginationResponse<User>> getUsers(
                        @Parameter(description = "Optional name filter (case-insensitive, partial match on firstName or lastName)", example = "John") @RequestParam(required = false) String name,

                        @Parameter(description = "Optional username filter (case-insensitive, partial match on username)", example = "emilys") @RequestParam(required = false) String username,

                        @Parameter(description = "Page number (1-indexed)", example = "1") @RequestParam(required = false, defaultValue = "1") Integer page,

                        @Parameter(description = "Number of items per page", example = "10") @RequestParam(required = false, defaultValue = "10") Integer size) {

                log.info("GET /api/users - name: '{}', username: '{}', page: {}, size: {}", name, username, page, size);

                // Create query object with validation
                UserQuery query = UserQuery.of(name, username, page, size);
                PaginationResponse<User> response = userService.getUsers(query);

                log.debug("Returning {} users for page {} (total filtered: {})",
                                response.data().size(), page, response.totalItems());

                return ResponseEntity.ok(response);
        }
}
