package com.project.pk48.inventoryms_springboot_api.security.repositories;

import com.project.pk48.inventoryms_springboot_api.security.models.UserPrivilegeAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignmentRepository extends JpaRepository<UserPrivilegeAssignment, Long>
 {
   // public void deleteAll(List<UserPrivilegeAssignment> assigningAssignment) ;

  public   List<UserPrivilegeAssignment> findByuserid(Long userid);


     List<UserPrivilegeAssignment> findByUseridAndPrivilegeidIn(Long userid, List<Long> privilegeIds);
 }
