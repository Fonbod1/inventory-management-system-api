package com.project.pk48.inventoryms_springboot_api.security.services;

import com.project.pk48.inventoryms_springboot_api.repositories.UserRepository;
import com.project.pk48.inventoryms_springboot_api.security.models.Privilege;
import com.project.pk48.inventoryms_springboot_api.security.repositories.PrivilegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

import java.util.List;

@Service
public class PrivilegeService {

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private UserRepository userRepository;

    //Get All Roles
    public List<Privilege> findAll() {
        return privilegeRepository.findAll();
    }

    //Get Role By Id
    public Privilege findById(Long id) {
        return privilegeRepository.findById(id).orElse(null);
    }

    //Delete Role
    public void delete(Long id) {
        privilegeRepository.deleteById(id);
    }

    //Update Role
    public Privilege save(Privilege privilege) {
        return privilegeRepository.save(privilege);
    }


}
