package com.project.pk48.inventoryms_springboot_api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.pk48.inventoryms_springboot_api.models.User;
import com.project.pk48.inventoryms_springboot_api.security.AuthenticationService;
import com.project.pk48.inventoryms_springboot_api.security.controllers.RegistrationController;
import com.project.pk48.inventoryms_springboot_api.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RegistrationControllerTest {

    private MockMvc mockMvc;
    private UserService userService;
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        authenticationService = mock(AuthenticationService.class);
        RegistrationController registrationController = new RegistrationController(authenticationService, userService);
        mockMvc = MockMvcBuilders.standaloneSetup(registrationController).build();
    }

    @Test
    void testRegisterSuccess() throws Exception {
        User user = new User();
        user.setUsername("testuser");
        user.setPasswordHash("password");

        when(userService.addNew(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("testuser"));
    }

    @Test
    void testRegisterFailure() throws Exception {
        User user = new User();
        user.setUsername("testuser");
        user.setPasswordHash("password");

        when(userService.addNew(any(User.class))).thenReturn(null);

        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isBadRequest());
    }
}