package com.project.pk48.inventoryms_springboot_api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.pk48.inventoryms_springboot_api.models.User;
import com.project.pk48.inventoryms_springboot_api.security.AuthenticationService;
import com.project.pk48.inventoryms_springboot_api.security.controllers.AuthController;
import com.project.pk48.inventoryms_springboot_api.security.models.LoginRequest;
import com.project.pk48.inventoryms_springboot_api.security.services.TokenService;
import com.project.pk48.inventoryms_springboot_api.security.services.UserPrivilegeAssignmentService;
import com.project.pk48.inventoryms_springboot_api.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AuthControllerTest {

    private MockMvc mockMvc;
    private UserService userService;
    private AuthenticationService authenticationService;
    private UserPrivilegeAssignmentService userPrivilegeAssignmentService;
    private TokenService tokenService;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        authenticationService = mock(AuthenticationService.class);
        userPrivilegeAssignmentService = mock(UserPrivilegeAssignmentService.class);
        tokenService = mock(TokenService.class);

        AuthController authController = new AuthController(
                userService,
                authenticationService,
                userPrivilegeAssignmentService,
                tokenService
        );
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    void testLoginSuccess() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("testuser");
        loginRequest.setPassword("testpass");

        User user = new User();
        user.setUsername("testuser");

        when(authenticationService.authenticate("testuser", "testpass")).thenReturn(true);
        when(userService.getUserByUsername("testuser")).thenReturn(user);
        when(tokenService.generateToken(any())).thenReturn("jwt-token");

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loginRequest))
                        .session(new MockHttpSession()))
                .andExpect(status().isOk())
                .andExpect(content().string("jwt-token"));
    }

    @Test
    void testLoginFailure() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("testuser");
        loginRequest.setPassword("wrongpass");

        when(authenticationService.authenticate("testuser", "wrongpass")).thenReturn(false);

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loginRequest))
                        .session(new MockHttpSession()))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Invalid username or password"));
    }

    @Test
    void testLogout() throws Exception {
        mockMvc.perform(post("/logout").session(new MockHttpSession()))
                .andExpect(status().isOk())
                .andExpect(content().string("Logout successful"));
    }
}