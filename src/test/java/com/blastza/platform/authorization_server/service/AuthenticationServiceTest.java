package com.blastza.platform.authorization_server.service;

import com.blastza.platform.authorization_server.config.JwtService;
import com.blastza.platform.authorization_server.models.RegisterRequest;
import com.blastza.platform.authorization_server.repository.UserRepository;
import com.blastza.platform.authorization_server.user.Role;
import com.blastza.platform.authorization_server.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(AuthenticationService.class)
public class AuthenticationServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private AuthenticationService userService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private AuthenticationManager authenticationManager;


    @Test
    void shouldRegisterUserAndReturnToken() throws Exception{
        // Arrange
        var user = User.builder()
                .firstname("lutendo")
                .lastname("damuleli")
                .password("password")
                .email("lutendo@test.com")
                .role(Role.USER)
                .build();

        when(userRepository.save(any(User.class))).thenReturn(any());
        when(jwtService.generateToken(user)).thenReturn("mockJwtToken");

        // Act
        var registerRequest = new RegisterRequest(
                user.getFirstname(),
                user.getLastname(),
                user.getEmail(),
                passwordEncoder.encode(user.getPassword())
        );
        String token = userService.register(registerRequest).getToken();

        // Assert
        assertNotNull(token);
        assertEquals("mockJwtToken", token);
    }
}
