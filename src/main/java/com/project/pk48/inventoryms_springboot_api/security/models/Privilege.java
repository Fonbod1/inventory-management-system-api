package com.project.pk48.inventoryms_springboot_api.security.models;

import com.fasterxml.jackson.annotation.*;
import com.project.pk48.inventoryms_springboot_api.models.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property =( "id"))
public class Privilege extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    /*
    @ManyToOne
    @JoinColumn(name = "userid",insertable = false, updatable = false)
    private User user;
    private Long userid; // the field that represent the joint table
     */
    @ManyToOne
    @JoinColumn(name = "roleid",insertable = false, updatable = false)
    @JsonBackReference
    private Role role;
    private Long roleid; // the field that represent the joint table


    @OneToMany(mappedBy = "privilege")
    @JsonIgnore
    private List<UserPrivilegeAssignment> users;

}
