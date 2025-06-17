package com.project.pk48.inventoryms_springboot_api.security.controllers;

import com.project.pk48.inventoryms_springboot_api.models.User;
import com.project.pk48.inventoryms_springboot_api.security.models.Privilege;
import com.project.pk48.inventoryms_springboot_api.security.models.UserPrivilegeAssignment;
import com.project.pk48.inventoryms_springboot_api.security.services.PrivilegeService;
import com.project.pk48.inventoryms_springboot_api.security.services.UserPrivilegeAssignmentService;
import com.project.pk48.inventoryms_springboot_api.services.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Transactional
@RestController
@RequestMapping
public class UserPrivilegAssignmentController {
    @Autowired
    private UserPrivilegeAssignmentService userPrivilegeAssignmentService;

    @Autowired
    private UserService userService;

    @GetMapping("/userprivilegeassignment")
    public List<UserPrivilegeAssignment> parameters(Model model) {
        return userPrivilegeAssignmentService.findAll();
    }

    @GetMapping("/userprivilegeassignment/{id}")
    @ResponseBody
    public UserPrivilegeAssignment getById(@PathVariable Long id) {
        return userPrivilegeAssignmentService.findById(id);
    }

    @PostMapping("/userprivilegeassignments")
    public UserPrivilegeAssignment save(UserPrivilegeAssignment userPrivilegeAssignment) {
        return userPrivilegeAssignmentService.save(userPrivilegeAssignment);
    }

    @RequestMapping(value = "/userprivilegeassignment/delete/{id}", method = {RequestMethod.DELETE, RequestMethod.GET})
    public void delete(@PathVariable Long id) {
        userPrivilegeAssignmentService.delete(id);

    }
    @Transactional
    @PostMapping("/user/{userid}/privileges")
    public ResponseEntity<String> savePrivileges(@PathVariable("userid") Long userid, @RequestBody List<Privilege> privileges) {
        try {
            List<Privilege> savePrivileges =  userPrivilegeAssignmentService.savePrivileges(userid, privileges);
            return ResponseEntity.status(HttpStatus.CREATED).body("Privileges assigned successfully: " + savePrivileges);
        }catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error assigning privileges: " + ex.getMessage());

        }

    }
    @GetMapping("/privilege/{privilegeid}/users")
  public List<Privilege> getUserPrivileges(@PathVariable("privilegeid") Long privilegeid){
       return userPrivilegeAssignmentService.getUserPrivileges(privilegeid);
    }
    @GetMapping("/user/{userid}/users")
    public List<User> getUsersByPrivilege(@PathVariable("userid") Long userid){
        return userPrivilegeAssignmentService.getUserByPrivilege(userid);
    }


    @DeleteMapping("/user/{userid}/privileges/clear")
    public ResponseEntity<String> clearUserPrivileges(@PathVariable("userid") Long userId) {
        try {
            userPrivilegeAssignmentService.deletePrivileges(userId);
            return ResponseEntity.status(HttpStatus.CREATED).body("Privileges cleared successfully");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error clearing privileges: " + ex.getMessage());
        }
    }

}
