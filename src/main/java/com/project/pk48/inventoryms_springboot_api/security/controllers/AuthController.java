package com.project.pk48.inventoryms_springboot_api.security.controllers;
import com.project.pk48.inventoryms_springboot_api.models.User;
import com.project.pk48.inventoryms_springboot_api.security.AuthenticationService;
import com.project.pk48.inventoryms_springboot_api.security.models.LoginRequest;
import com.project.pk48.inventoryms_springboot_api.security.models.UserPrincipal;
import com.project.pk48.inventoryms_springboot_api.security.services.TokenService;
import com.project.pk48.inventoryms_springboot_api.security.services.UserPrivilegeAssignmentService;
import com.project.pk48.inventoryms_springboot_api.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RequestMapping
@RestController
public class AuthController {

    private final UserService userService;

    private final AuthenticationService authenticationService;

    private  final UserPrivilegeAssignmentService userPrivilegeAssignmentService;
    private final TokenService tokenService;
    public AuthController(UserService userService, AuthenticationService authenticationService, UserPrivilegeAssignmentService userPrivilegeAssignmentService, TokenService tokenService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
        this.userPrivilegeAssignmentService = userPrivilegeAssignmentService;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest, HttpSession session) {
        try {
            boolean isAuthenticated = authenticationService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
            if (isAuthenticated) {
                //create a user
                User user = userService.getUserByUsername(loginRequest.getUsername());
                        //.orElseThrow(() -> new RuntimeException("User not found"));
                //create the user principal so that ypu can get GrantedAuthority
                UserPrincipal userPrincipal = new UserPrincipal(userPrivilegeAssignmentService, user);


                //Generate taken when the user login
                Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, userPrincipal.getAuthorities());
                //  create the token now
                String jwtToken = tokenService.generateToken(authentication);

                session.setAttribute("user", loginRequest.getUsername());
                return ResponseEntity.ok(jwtToken);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during login");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();  // Invalidate the session
        return ResponseEntity.ok("Logout successful");
    }

}
