package com.project.pk48.inventoryms_springboot_api.repositories;

import com.project.pk48.inventoryms_springboot_api.models.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    void testSaveAndFindById() {
        Task task = new Task();
        // Set required fields for Task here, e.g.:
        // task.setName("Inventory Check");
        Task saved = taskRepository.save(task);

        Optional<Task> found = taskRepository.findById(saved.getId().longValue());
        assertTrue(found.isPresent());
        // assertEquals("Inventory Check", found.get().getName());
    }
}