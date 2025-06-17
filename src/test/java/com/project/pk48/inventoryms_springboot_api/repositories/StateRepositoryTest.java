package com.project.pk48.inventoryms_springboot_api.repositories;

import com.project.pk48.inventoryms_springboot_api.models.State;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class StateRepositoryTest {

    @Autowired
    private StateRepository stateRepository;

    @Test
    void testSaveAndFindById() {
        State state = new State();
        state.setName("California"); // Set other required fields if needed

        State saved = stateRepository.save(state);

        Optional<State> found = stateRepository.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals("California", found.get().getName());
    }
}