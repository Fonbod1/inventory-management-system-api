package com.project.pk48.inventoryms_springboot_api.security.services;

import com.project.pk48.inventoryms_springboot_api.models.User;
import com.project.pk48.inventoryms_springboot_api.repositories.UserRepository;
import com.project.pk48.inventoryms_springboot_api.security.models.Privilege;
import com.project.pk48.inventoryms_springboot_api.security.models.Role;
import com.project.pk48.inventoryms_springboot_api.security.models.UserPrivilegeAssignment;
import com.project.pk48.inventoryms_springboot_api.security.repositories.UserPrivilegeAssignmentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Transactional
@Service
public class UserPrivilegeAssignmentService {

    @Autowired
    private UserPrivilegeAssignmentRepository userPrivilegeAssignmentRepository;

    @Autowired
    private UserRepository userRepository;

    //Get All Roles
    public List<UserPrivilegeAssignment> findAll() {
        return userPrivilegeAssignmentRepository.findAll();
    }

    //Get Role By Id
    public UserPrivilegeAssignment findById(Long id) {
        return userPrivilegeAssignmentRepository.findById(id).orElse(null);
    }

    //Delete Role
    public void delete(Long id) {
        userPrivilegeAssignmentRepository.deleteById(id);
    }

    //Update Role
    public UserPrivilegeAssignment save(UserPrivilegeAssignment userPrivilegeAssignment) {
        return userPrivilegeAssignmentRepository.save(userPrivilegeAssignment);
    }
    //method to save a user privileges assignment
    @Transactional
    public List<Privilege> savePrivileges(Long userid, List<Privilege> privileges){
userPrivilegeAssignmentRepository.deleteByUserId(userid);
   List<UserPrivilegeAssignment> assignments = privileges.stream()
                .map(privilege -> new UserPrivilegeAssignment(userid, privilege.getId()))
                .toList();
        return userPrivilegeAssignmentRepository.saveAll(assignments).stream()
                .map(UserPrivilegeAssignment::getPrivilege)
                .toList();

    }

    // A delete method to remove all privileges for a user
    public void deletePrivileges(Long userid) {
        userPrivilegeAssignmentRepository.deleteByUserId(userid);
        }

    public List<Privilege> getUserPrivileges(Long userid) {
        List<UserPrivilegeAssignment> assignments = userPrivilegeAssignmentRepository.findByUserId(userid);
     return assignments.stream()
             .map(UserPrivilegeAssignment::getPrivilege)
             .toList();


    }

    public List<User> getUserByPrivilege(Long privilegeid) {
        List<UserPrivilegeAssignment> assignments = userPrivilegeAssignmentRepository.findByUserId(privilegeid);
        return assignments.stream()
                .map(UserPrivilegeAssignment::getUser)
                .toList();
    }
}



