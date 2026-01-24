package com.example.pagination.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.pagination.dto.PaginationResponse;
import com.example.pagination.dto.UserQuery;
import com.example.pagination.model.User;
import com.example.pagination.service.UserCacheService;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserCacheService userCacheService;

    @InjectMocks
    private UserServiceImpl userService;

    private List<User> mockUsers;

    @BeforeEach
    void setUp() {
        mockUsers = createMockUsers(100);
    }

    @Test
    void testGetUsers_FirstPage() {
        // Given
        when(userCacheService.getAllUsers()).thenReturn(mockUsers);

        // When
        PaginationResponse<User> response = userService.getUsers(UserQuery.of(null, null, 1, 10));

        // Then
        assertNotNull(response);
        assertEquals(1, response.page());
        assertEquals(10, response.size());
        assertEquals(100, response.totalItems());
        assertEquals(10, response.totalPages());
        assertEquals(10, response.data().size());
        assertEquals(1L, response.data().get(0).id());
    }

    @Test
    void testGetUsers_SecondPage() {
        // Given
        when(userCacheService.getAllUsers()).thenReturn(mockUsers);

        // When
        PaginationResponse<User> response = userService.getUsers(UserQuery.of(null, null, 2, 10));

        // Then
        assertNotNull(response);
        assertEquals(2, response.page());
        assertEquals(10, response.size());
        assertEquals(100, response.totalItems());
        assertEquals(10, response.totalPages());
        assertEquals(10, response.data().size());
        assertEquals(11L, response.data().get(0).id());
    }

    @Test
    void testGetUsers_LastPage() {
        // Given
        when(userCacheService.getAllUsers()).thenReturn(mockUsers);

        // When
        PaginationResponse<User> response = userService.getUsers(UserQuery.of(null, null, 10, 10));

        // Then
        assertNotNull(response);
        assertEquals(10, response.page());
        assertEquals(10, response.size());
        assertEquals(100, response.totalItems());
        assertEquals(10, response.totalPages());
        assertEquals(10, response.data().size());
        assertEquals(91L, response.data().get(0).id());
    }

    @Test
    void testGetUsers_PageBeyondTotal() {
        // Given
        when(userCacheService.getAllUsers()).thenReturn(mockUsers);

        // When
        PaginationResponse<User> response = userService.getUsers(UserQuery.of(null, null, 100, 10));

        // Then
        assertNotNull(response);
        assertEquals(100, response.page());
        assertEquals(10, response.size());
        assertEquals(100, response.totalItems());
        assertEquals(10, response.totalPages());
        assertEquals(0, response.data().size());
    }

    @Test
    void testGetUsers_InvalidPage_Zero() {
        // When & Then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.getUsers(UserQuery.of(null, null, 0, 10)));
        assertEquals("Page must be greater than 0", exception.getMessage());
    }

    @Test
    void testGetUsers_InvalidPage_Negative() {
        // When & Then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.getUsers(UserQuery.of(null, null, -1, 10)));
        assertEquals("Page must be greater than 0", exception.getMessage());
    }

    @Test
    void testGetUsers_InvalidSize_Zero() {
        // When & Then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.getUsers(UserQuery.of(null, null, 1, 0)));
        assertEquals("Size must be greater than 0", exception.getMessage());
    }

    @Test
    void testGetUsers_InvalidSize_Negative() {
        // When & Then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.getUsers(UserQuery.of(null, null, 1, -1)));
        assertEquals("Size must be greater than 0", exception.getMessage());
    }

    @Test
    void testGetUsers_CustomPageSize() {
        // Given
        when(userCacheService.getAllUsers()).thenReturn(mockUsers);

        // When
        PaginationResponse<User> response = userService.getUsers(UserQuery.of(null, null, 1, 25));

        // Then
        assertNotNull(response);
        assertEquals(1, response.page());
        assertEquals(25, response.size());
        assertEquals(100, response.totalItems());
        assertEquals(4, response.totalPages());
        assertEquals(25, response.data().size());
    }

    @Test
    void testGetUsers_PartialLastPage() {
        // Given
        List<User> users = createMockUsers(23);
        when(userCacheService.getAllUsers()).thenReturn(users);

        // When
        PaginationResponse<User> response = userService.getUsers(UserQuery.of(null, null, 3, 10));

        // Then
        assertNotNull(response);
        assertEquals(3, response.page());
        assertEquals(10, response.size());
        assertEquals(23, response.totalItems());
        assertEquals(3, response.totalPages());
        assertEquals(3, response.data().size()); // Only 3 items on last page
    }

    @Test
    void testGetUsers_FilterByName() {
        // Given
        when(userCacheService.getAllUsers()).thenReturn(mockUsers);

        // When (Filter by "FirstName1" - matches FirstName1, FirstName10-19,
        // FirstName100)
        // Adjust filter to match exactly one for simpler assertion
        PaginationResponse<User> response = userService.getUsers(UserQuery.of("FirstName50", null, 1, 10));

        // Then
        assertNotNull(response);
        assertEquals(1, response.totalItems());
        assertEquals(1, response.data().size());
        assertEquals("FirstName50", response.data().get(0).firstName());
    }

    private List<User> createMockUsers(int count) {
        List<User> users = new ArrayList<>();
        for (long i = 1; i <= count; i++) {
            users.add(new User(
                    i,
                    "FirstName" + i,
                    "LastName" + i,
                    25,
                    "user" + i + "@example.com",
                    "+1234567890",
                    "user" + i,
                    "1990-01-01",
                    "https://example.com/image.jpg",
                    "O+",
                    175.0,
                    70.0,
                    "Brown",
                    null,
                    "192.168.1.1",
                    null,
                    "00:00:00:00:00:00",
                    "University",
                    null,
                    null,
                    "123-456",
                    "123-45-6789",
                    "Mozilla/5.0",
                    null,
                    "user"));
        }
        return users;
    }
}
