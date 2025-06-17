package com.project.pk48.inventoryms_springboot_api.service;

import com.project.pk48.inventoryms_springboot_api.models.User;
import com.project.pk48.inventoryms_springboot_api.repositories.UserRepository;
import com.project.pk48.inventoryms_springboot_api.security.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthenticationServiceTest {

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserRepository userRepository;
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        bCryptPasswordEncoder = mock(BCryptPasswordEncoder.class);
        userRepository = mock(UserRepository.class);
        authenticationService = new AuthenticationService(bCryptPasswordEncoder, userRepository);
    }

    @Test
    void testAuthenticateSuccess() {
        User user = new User();
        user.setUsername("testuser");
        user.setPasswordHash("hashedPassword");
        when(userRepository.findByUsername("testuser")).thenReturn(user);
        when(bCryptPasswordEncoder.encode("plainPassword")).thenReturn("hashedPassword");

        assertTrue(authenticationService.authenticate("testuser", "plainPassword"));
    }

    @Test
    void testAuthenticateUserNotFound() {
        User user = new User();
        user.setUsername("otheruser");
        when(userRepository.findByUsername("testuser")).thenReturn(user);

        assertThrows(UsernameNotFoundException.class, () ->
                authenticationService.authenticate("testuser", "anyPassword")
        );
    }

    @Test
    void testAuthenticateBadCredentials() {
        User user = new User();
        user.setUsername("testuser");
        user.setPasswordHash("hashedPassword");
        when(userRepository.findByUsername("testuser")).thenReturn(user);
        when(bCryptPasswordEncoder.encode("wrongPassword")).thenReturn("wrongHash");

        assertThrows(BadCredentialsException.class, () ->
                authenticationService.authenticate("testuser", "wrongPassword")
        );
    }
}