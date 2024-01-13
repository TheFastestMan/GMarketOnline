package ru.rail.gmarketonline.integration;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ru.rail.gmarketonline.dto.UserDto;
import ru.rail.gmarketonline.entity.Gender;
import ru.rail.gmarketonline.entity.Role;
import ru.rail.gmarketonline.service.UserService;

import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
public class UserServiceIT {
    @Autowired
    private UserService userService;

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
        // When
        List<UserDto> users = userService.findAllUser();

        // Then
        assertNotNull(users, "The list of users should not be null");
    }

    @Test
    public void testCreateUser() {
        // Set
        UserDto userDto = UserDto.builder()
                .username("12testUser")
                .email("12test@example.com")
                .role(Role.USER)
                .gender(Gender.MALE)
                .password("12password")
                .build();

        // Act
        userService.create(userDto);

        // Assert
        assertEquals("Emails should match", userDto.getEmail(), "12test@example.com");
        assertEquals("Username should match", userDto.getUsername(), "12testUser");
        assertEquals("Passwords should match", userDto.getPassword(), "12password");
        assertEquals("Role should match", userDto.getRole(), Role.USER);
        assertEquals("Gender should match", userDto.getGender(), Gender.MALE);
    }

}
