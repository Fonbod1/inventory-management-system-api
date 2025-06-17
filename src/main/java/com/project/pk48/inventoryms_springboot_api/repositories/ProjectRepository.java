package com.project.pk48.inventoryms_springboot_api.repositories;
import com.project.pk48.inventoryms_springboot_api.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
}
