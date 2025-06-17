package com.project.pk48.inventoryms_springboot_api.repositories;

import com.project.pk48.inventoryms_springboot_api.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testSaveAndFindByUsername() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password"); // Set other required fields if needed

        User saved = userRepository.save(user);

        User found = userRepository.findByUsername("testuser");
        assertNotNull(found);
        assertEquals("testuser", found.getUsername());
    }
}