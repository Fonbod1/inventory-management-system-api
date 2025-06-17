package com.project.pk48.inventoryms_springboot_api.service;

import com.project.pk48.inventoryms_springboot_api.models.Project;
import com.project.pk48.inventoryms_springboot_api.repositories.ProjectRepository;
import com.project.pk48.inventoryms_springboot_api.services.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProjectServiceTest {

    private ProjectRepository projectRepository;
    private ProjectService projectService;

    @BeforeEach
    void setUp() {
        projectRepository = mock(ProjectRepository.class);
        projectService = new ProjectService(projectRepository);
    }

    @Test
    void testGetAllProjects() {
        when(projectRepository.findAll()).thenReturn(Arrays.asList(new Project(), new Project()));
        assertEquals(2, projectService.getAllProjects().size());
    }

    @Test
    void testGetProjectById() {
        Project project = new Project();
        project.setId(1);
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        assertNotNull(projectService.getProjectById(1L));
    }

    @Test
    void testSave() {
        Project project = new Project();
        when(projectRepository.save(project)).thenReturn(project);
        assertEquals(project, projectService.save(project));
    }

    @Test
    void testDeleteProject() {
        doNothing().when(projectRepository).deleteById(1L);
        projectService.deleteProject(1L);
        verify(projectRepository, times(1)).deleteById(1L);
    }
}