package com.project.pk48.inventoryms_springboot_api.security.services;
import com.project.pk48.inventoryms_springboot_api.models.User;
import com.project.pk48.inventoryms_springboot_api.repositories.UserRepository;
import com.project.pk48.inventoryms_springboot_api.security.models.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserPrivilegeAssignmentService userPrivilegeAssignmentService;
    @Autowired
    public MyUserDetailsService(UserRepository userRepository, UserPrivilegeAssignmentService userPrivilegeAssignmentService) {
        this.userRepository = userRepository;
        this.userPrivilegeAssignmentService = userPrivilegeAssignmentService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if(user == null) {
            throw  new UsernameNotFoundException("User not found in the database");
        }
        return new UserPrincipal(userPrivilegeAssignmentService, user);
    }
}
