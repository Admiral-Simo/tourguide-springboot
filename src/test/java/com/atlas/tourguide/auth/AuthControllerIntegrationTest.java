package com.atlas.tourguide.auth;

import com.atlas.tourguide.auth.dtos.AuthResponseDto;
import com.atlas.tourguide.auth.dtos.LoginRequestDto;
import com.atlas.tourguide.auth.dtos.SignupRequestDto;
import com.atlas.tourguide.user.User;
import com.atlas.tourguide.user.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers // Enables JUnit 5 extension for Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthControllerIntegrationTest {
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.datasource.driver-class-name", postgres::getDriverClassName);
        registry.add("jwt.secret", () -> "a-very-long-and-secure-secret-key-for-testing-purposes");
    }

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void signup_Success() {
        // Arrange
        SignupRequestDto signupRequest = new SignupRequestDto(
                "testuser@example.com",
                "password123",
                "Test User"
        );

        // Act
        ResponseEntity<AuthResponseDto> response = restTemplate.postForEntity(
                "/api/v1/auth/signup",
                signupRequest,
                AuthResponseDto.class
        );

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getToken()).isNotBlank();
        assertThat(response.getBody().getExpiresIn()).isEqualTo(86400);

        // Verify user was created in the database
        User savedUser = userRepository.findByEmail("testuser@example.com").orElse(null);
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getName()).isEqualTo("Test User");
        assertThat(passwordEncoder.matches("password123", savedUser.getPassword())).isTrue();
    }

    @Test
    void login_Success() {
        // Arrange
        User user = User.builder()
                .email("loginuser@example.com")
                .password(passwordEncoder.encode("password123"))
                .name("Login User")
                .build();
        userRepository.save(user);

        LoginRequestDto loginRequest = new LoginRequestDto("loginuser@example.com", "password123");

        // Act
        ResponseEntity<AuthResponseDto> response = restTemplate.postForEntity(
                "/api/v1/auth/login",
                loginRequest,
                AuthResponseDto.class
        );

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getToken()).isNotBlank();
    }

    @Test
    void login_FailsWithInvalidPassword() {
        // Arrange
        User user = User.builder()
                .email("loginuser@example.com")
                .password(passwordEncoder.encode("password123"))
                .name("Login User")
                .build();
        userRepository.save(user);

        LoginRequestDto loginRequest = new LoginRequestDto("loginuser@example.com", "wrong-password");

        // Act
        ResponseEntity<String> response = restTemplate.postForEntity(
                "/api/v1/auth/login",
                loginRequest,
                String.class
        );

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }
}