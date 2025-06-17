package com.project.pk48.inventoryms_springboot_api.security.repositories;

import com.project.pk48.inventoryms_springboot_api.security.models.UserPrivilegeAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserPrivilegeAssignmentRepository extends JpaRepository<UserPrivilegeAssignment, Long> {
    // qurery to delete by user id
    public void deleteByUserId(Long userId);

   public List<UserPrivilegeAssignment> findByUserId(Long userid);
}
