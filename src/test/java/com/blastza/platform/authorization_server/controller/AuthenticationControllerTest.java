package com.blastza.platform.authorization_server.controller;

import com.blastza.platform.authorization_server.config.JwtService;
import com.blastza.platform.authorization_server.models.AuthenticationRequest;
import com.blastza.platform.authorization_server.models.AuthenticationResponse;
import com.blastza.platform.authorization_server.models.RegisterRequest;
import com.blastza.platform.authorization_server.models.RegistrationResponse;
import com.blastza.platform.authorization_server.service.AuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@WebMvcTest(AuthenticationController.class)
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private AuthenticationService service;

    @Test
    @WithMockUser(username = "Lutendo", roles = "USER")
    void registerUserAndReturnTokenSuccess() throws Exception {
        // Arrange
        var registerRequest = new RegisterRequest(
                "Lutendo",
                "Damuleli",
                "lutendo@gmail.com",
                "password"
        );
        when(service.register(any(RegisterRequest.class)))
                .thenReturn(new RegistrationResponse("mockJwtToken","works"));

        // Act & Assert
        mockMvc.perform(post("/api/v1/auth/register").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(registerRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("mockJwtToken"))
                .andExpect(jsonPath("$.message").value("works"));
    }

    @Test
    @WithMockUser(username = "Lutendo", roles = "USER")
    void authenticateUserAndReturnTokenSuccess() throws Exception {
        // Arrange
        var authRequest = new AuthenticationRequest("lutendo@gmail.com", "password");
        when(service.authenticate(any(AuthenticationRequest.class)))
                .thenReturn(new AuthenticationResponse("mockJwtToken"));

        // Act & Assert
        mockMvc.perform(post("/api/v1/auth/authenticate").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(authRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("mockJwtToken"));
    }
}
