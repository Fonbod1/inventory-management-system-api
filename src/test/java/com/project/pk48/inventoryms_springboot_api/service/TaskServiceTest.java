package com.project.pk48.inventoryms_springboot_api.service;

import com.project.pk48.inventoryms_springboot_api.models.Task;
import com.project.pk48.inventoryms_springboot_api.repositories.TaskRepository;
import com.project.pk48.inventoryms_springboot_api.services.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    private TaskRepository taskRepository;
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        taskRepository = mock(TaskRepository.class);
        taskService = new TaskService(taskRepository);
    }

    @Test
    void testGetAllTasks() {
        when(taskRepository.findAll()).thenReturn(Arrays.asList(new Task(), new Task()));
        assertEquals(2, taskService.getAllTasks().size());
    }

    @Test
    void testGetTaskById() {
        Task task = new Task();
        task.setId(1);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        assertNotNull(taskService.getTaskById(1L));
    }

    @Test
    void testSave() {
        Task task = new Task();
        when(taskRepository.save(task)).thenReturn(task);
        assertEquals(task, taskService.save(task));
    }

    @Test
    void testDeleteTask() {
        doNothing().when(taskRepository).deleteById(1L);
        taskService.deleteTask(1L);
        verify(taskRepository, times(1)).deleteById(1L);
    }
}