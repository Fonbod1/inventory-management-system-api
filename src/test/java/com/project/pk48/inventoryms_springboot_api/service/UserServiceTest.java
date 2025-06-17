package com.project.pk48.inventoryms_springboot_api.service;
import com.project.pk48.inventoryms_springboot_api.models.User;
import com.project.pk48.inventoryms_springboot_api.repositories.UserRepository;
import com.project.pk48.inventoryms_springboot_api.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        bCryptPasswordEncoder = mock(BCryptPasswordEncoder.class);
        userService = new UserService(userRepository, bCryptPasswordEncoder);
    }

    @Test
    void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(new User(), new User()));
        assertEquals(2, userService.getAllUsers().size());
    }

    @Test
    void testGetUserById() {
        User user = new User();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        assertNotNull(userService.getUserById(1L));
    }

    @Test
    void testAddNew() {
        User user = new User();
        user.setPassword("plainPassword");
        when(bCryptPasswordEncoder.encode("plainPassword")).thenReturn("hashedPassword");
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));
        User savedUser = userService.addNew(user);
        assertEquals("hashedPassword", savedUser.getPassword());
        assertEquals("hashedPassword", savedUser.getPasswordHash());
    }

    @Test
    void testUpdateUser() {
        User user = new User();
        when(userRepository.save(user)).thenReturn(user);
        assertEquals(user, userService.updateUser(user, 1L));
    }

    @Test
    void testDeleteUser() {
        doNothing().when(userRepository).deleteById(1L);
        userService.deleteUser(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void testGetUserByUsername() {
        User user = new User();
        when(userRepository.findByUsername("testuser")).thenReturn(user);
        assertEquals(user, userService.getUserByUsername("testuser"));
    }
}