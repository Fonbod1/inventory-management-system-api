package com.project.pk48.inventoryms_springboot_api.security.models;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.project.pk48.inventoryms_springboot_api.models.User;
import com.project.pk48.inventoryms_springboot_api.security.services.UserPrivilegeAssignmentService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property =( "id"))
public class UserPrincipal implements UserDetails {
    private final UserPrivilegeAssignmentService userPrivilegeAssignmentService;

    private final User user;
    public UserPrincipal(UserPrivilegeAssignmentService userPrivilegeAssignmentService, User user) {
        this.userPrivilegeAssignmentService = userPrivilegeAssignmentService;
        this.user = user;
    }

    //We are going to use this class to return all user privileges form the database
    // and assign them to the user with granted authority
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       // return Collections.singleton(new SimpleGrantedAuthority("USER"));
     //Get all the user privileges from the database
       List<Privilege> privilegeList = userPrivilegeAssignmentService.getUserPrivileges(user.getId());
return privilegeList.stream()
                .map(privilege -> new SimpleGrantedAuthority(privilege.getDescription()))
                .collect(Collectors.toList());


    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}