package com.project.pk48.inventoryms_springboot_api.security.controllers;
import com.project.pk48.inventoryms_springboot_api.models.User;
import com.project.pk48.inventoryms_springboot_api.security.AuthenticationService;
import com.project.pk48.inventoryms_springboot_api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RequestMapping
@RestController
public class RegistrationController {

    private final AuthenticationService authenticationService;

    private final UserService userService;

    @Autowired
    public RegistrationController(AuthenticationService authenticationService, UserService userService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        User savedUser = userService.addNew(user);
        if (savedUser != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // 400 Bad Request
        }
    }

}