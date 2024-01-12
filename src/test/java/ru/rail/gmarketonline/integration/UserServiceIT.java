package ru.rail.gmarketonline.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.rail.gmarketonline.dto.UserDto;
import ru.rail.gmarketonline.entity.User;
import ru.rail.gmarketonline.repository.UserRepository;
import ru.rail.gmarketonline.service.UserService;

import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
@Transactional
public class UserServiceIT {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    // Additional setup methods if needed

    @BeforeEach
    public void setUp() {
        // This method is called before each test.
        // You can add users to the test database here if you don't want to use @Sql annotation.
    }

    @Test
    public void testLoginSuccess() {
        // Given
        // Assume a user has been setup in your test database
        String email = "test@example.com";
        String password = "securepassword";

        // When
        Optional<UserDto> result = userService.login(email, password);

        // Then
        assertTrue(result.isPresent(), "User should be found");
        assertEquals("testuser", result.get().getUsername(), "Usernames should match");
    }

    @Test
    public void testLoginFailure() {
        // Given
        String email = "nonexistent@example.com";
        String password = "wrongpassword";

        // When
        Optional<UserDto> result = userService.login(email, password);

        // Then
        assertFalse(result.isPresent(), "User should not be found with wrong credentials");
    }

    @Test
    public void testFindAllUser() throws Exception {
        // Given
        // Assume users have been setup in your test database

        // When
        List<UserDto> users = userService.findAllUser();

        // Then
        assertNotNull(users, "The list of users should not be null");
        // Add more assertions as needed
    }

//    @Test
//    public void testCreateUser() {
//        // Given
//        UserDto newUser = new UserDto();
//        // Set user details for newUser
//
//        // When
//        User createdUser = userService.create(newUser);
//
//        // Then
//        assertNotNull(createdUser, "The created user should not be null");
//        // Add more assertions as needed
//    }

    // Additional tests for validation, error cases, etc.
}
