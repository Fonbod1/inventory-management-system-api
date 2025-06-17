package com.project.pk48.inventoryms_springboot_api.repositories;

import com.project.pk48.inventoryms_springboot_api.models.Location;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class LocationRepositoryTest {

    @Autowired
    private LocationRepository locationRepository;

    @Test
    void testSaveAndFindById() {
        Location location = new Location();
        // Set required fields for Location here, e.g.:
        // location.setName("Warehouse A");
        Location saved = locationRepository.save(location);

        Optional<Location> found = locationRepository.findById(saved.getId());
        assertTrue(found.isPresent());
        // assertEquals("Warehouse A", found.get().getName());
    }
}