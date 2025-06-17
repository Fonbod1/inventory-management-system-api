package com.project.pk48.inventoryms_springboot_api.security.services;

import com.project.pk48.inventoryms_springboot_api.repositories.UserRepository;
import com.project.pk48.inventoryms_springboot_api.security.models.Privilege;
import com.project.pk48.inventoryms_springboot_api.security.models.Role;
import com.project.pk48.inventoryms_springboot_api.security.models.UserPrivilegeAssignment;
import com.project.pk48.inventoryms_springboot_api.security.repositories.AssignmentRepository;
import com.project.pk48.inventoryms_springboot_api.security.repositories.PrivilegeRepository;
import com.project.pk48.inventoryms_springboot_api.security.repositories.RoleRepository;
import com.project.pk48.inventoryms_springboot_api.security.repositories.UserPrivilegeAssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private final RoleRepository roleRepository;

    private final UserPrivilegeAssignmentRepository userPrivilegeAssignmentRepository;
    private final UserRepository userRepository;

    private final PrivilegeRepository privilegeRepository;
    public final AssignmentRepository assigmentRepository;

    public RoleService(RoleRepository roleRepository, UserPrivilegeAssignmentRepository userPrivilegeAssignmentRepository, UserRepository userRepository, PrivilegeRepository privilegeRepository, AssignmentRepository assigmentRepository) {
        this.roleRepository = roleRepository;
        this.userPrivilegeAssignmentRepository = userPrivilegeAssignmentRepository;
        this.userRepository = userRepository;
        this.privilegeRepository = privilegeRepository;
        this.assigmentRepository = assigmentRepository;
    }

    //Get All Roles
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    //Get Role By Id
    public Role findById(Long id) {
        return roleRepository.findById(id).orElse(null);
    }

    //Delete Role
    public void delete(Long id) {
        roleRepository.deleteById(id);
    }

    //Update Role
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    public void assignUserRole(Long userid, Long roleid) {
        List <Privilege> privileges = privilegeRepository.findByRoleid(roleid);
      List<UserPrivilegeAssignment> existingAssignment = assigmentRepository.findByuserid(userid);
        assigmentRepository.deleteAll(existingAssignment);

      List<UserPrivilegeAssignment> assignments = privileges.stream()
              .map(privilege -> new
                      UserPrivilegeAssignment(userid, privilege.getId()))
              .toList();
              assigmentRepository.saveAll(assignments);
      //return privileges;
    }

    public void unAssignUserRole(Long userid, Long roleid) {
        List<Privilege> privileges = privilegeRepository.findByRoleid(roleid);
        List<Long> privilegeIds = privileges.stream()
                .map(Privilege::getId)
                .toList();

        List<UserPrivilegeAssignment> assignments = assigmentRepository
                .findByUseridAndPrivilegeidIn(userid, privilegeIds);

        assigmentRepository.deleteAll(assignments);
    }

    /*
    public void unAssignUserRole(Long userid, Long roleid) {
        List <Privilege> privileges = privilegeRepository.findByRoleid(roleid);
        List<UserPrivilegeAssignment> assignments = privileges.stream()
                .map(privilege -> new UserPrivilegeAssignment(userid, privilege.getId()))
                .toList();
     assigmentRepository.deleteAll(assignments);
    }*/

    public List<Privilege> getPrivilegesInRole(Long roleId) {
       return privilegeRepository.findByRoleid(roleId);
    }
}
