package com.project.pk48.inventoryms_springboot_api.security.repositories;

import com.project.pk48.inventoryms_springboot_api.security.models.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.print.attribute.standard.PageRanges;
import java.util.List;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {


    public List<Privilege> findByRoleid(Long roleid);



}

