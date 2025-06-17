package com.project.pk48.inventoryms_springboot_api.security.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.project.pk48.inventoryms_springboot_api.models.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property =( "id"))
public class UserPrivilegeAssignment {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    //@OneToMany(mappedBy = "role")
    //private List<Privilege> privileges;

    @ManyToOne
    @JoinColumn(name = "userid",insertable = false, updatable = false)
    private User user;
    private Long userid; // the field that represent the joint table

    @ManyToOne
    @JoinColumn(name = "privilegeid",insertable = false, updatable = false)
    private Privilege privilege;
    private Long privilegeid;

    public UserPrivilegeAssignment(Long userid, Long privilegeid) {
        this.userid = userid;
        this.privilegeid = privilegeid;
    }
}
