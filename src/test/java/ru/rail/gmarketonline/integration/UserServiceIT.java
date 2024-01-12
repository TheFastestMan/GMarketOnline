package ru.rail.gmarketonline.integration;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.transaction.annotation.Transactional;
import ru.rail.gmarketonline.dto.UserDto;
import ru.rail.gmarketonline.entity.Gender;
import ru.rail.gmarketonline.entity.Role;
import ru.rail.gmarketonline.entity.User;
import ru.rail.gmarketonline.repository.UserRepository;
import ru.rail.gmarketonline.service.UserService;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.any;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.web.client.ExpectedCount.never;

@SpringBootTest
@Transactional
public class UserServiceIT {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


    @BeforeEach
    public void setUp() {
        // This method is called before each test.
        // You can add users to the test database here if you don't want to use @Sql annotation.
    }

    @Test
    public void testLoginSuccess() throws Exception {
        // Set
        String email = "admin1@example.com";
        String password = "password1";

        // When
        Optional<UserDto> result = userService.login(email, password);

        // Then
        assertTrue(result.isPresent(), "User should be found");
        assertEquals("testuser", result.get().getUsername(), "admin1");
    }

    @Test
    public void testLoginFailure() throws Exception {
        // Set
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

    @Test
    public void testCreateUser() {
        // Arrange
        String username = "testUser";
        String email = "test@example.com";
        Role role = Role.USER; // Assuming Role is an enum or a class you have defined
        Gender gender = Gender.MALE; // Assuming Gender is an enum or a class you have defined
        String password = "password";

        UserDto userDto = UserDto.builder()
                .username(username)
                .email(email)
                .role(role)
                .gender(gender)
                .password(password)
                .build();

        // Act
        User createdUser = userService.create(userDto);

        // Assert
        assertNotNull(createdUser);
        assertNotNull(createdUser.getId());
        // Additional assertions as needed
    }




}
