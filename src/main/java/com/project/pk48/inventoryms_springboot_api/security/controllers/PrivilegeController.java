package com.project.pk48.inventoryms_springboot_api.security.controllers;

import com.project.pk48.inventoryms_springboot_api.security.models.Privilege;
import com.project.pk48.inventoryms_springboot_api.security.models.Role;
import com.project.pk48.inventoryms_springboot_api.security.repositories.PrivilegeRepository;
import com.project.pk48.inventoryms_springboot_api.security.services.PrivilegeService;
import com.project.pk48.inventoryms_springboot_api.security.services.RoleService;
import com.project.pk48.inventoryms_springboot_api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
//@RequestMapping("/api/v1")
public class PrivilegeController {

    @Autowired
    private PrivilegeService privilegeService;

    // ✅ Get all privileges
    @GetMapping("/privileges")
    public List<Privilege> getAllPrivileges() {
        return privilegeService.findAll();
    }

    // ✅ Get a privilege by ID
    @GetMapping("/privileges/{id}")
    public Privilege getById(@PathVariable Long id) {
        return privilegeService.findById(id);
    }

    // ✅ Create or update a privilege
    @PostMapping("/privileges")
    public Privilege save(@RequestBody Privilege privilege) {
        return privilegeService.save(privilege);
    }

    // update privilege
    @PutMapping("/privileges/{id}")
    public Privilege updateDescription(@PathVariable Long id, @RequestBody Map<String, String> body) {
        Privilege existing = privilegeService.findById(id);
        if (existing == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Privilege not found");
        }

        String newDescription = body.get("description");
        if (newDescription == null || newDescription.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Description is required");
        }

        existing.setDescription(newDescription);
        return privilegeService.save(existing);
    }




    // ✅ Delete a privilege
    @DeleteMapping("/privileges/{id}")
    public void delete(@PathVariable Long id) {
        privilegeService.delete(id);
    }


   //@PostMapping("/user/{id}/privileges")

}
