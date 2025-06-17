package com.project.pk48.inventoryms_springboot_api.service;

import com.project.pk48.inventoryms_springboot_api.models.Location;
import com.project.pk48.inventoryms_springboot_api.repositories.LocationRepository;
import com.project.pk48.inventoryms_springboot_api.services.LocationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LocationServiceTest {

    private LocationRepository locationRepository;
    private LocationService locationService;

    @BeforeEach
    void setUp() {
        locationRepository = mock(LocationRepository.class);
        locationService = new LocationService();
        // Inject mock using reflection since @Autowired is used
        try {
            java.lang.reflect.Field field = LocationService.class.getDeclaredField("locationRepository");
            field.setAccessible(true);
            field.set(locationService, locationRepository);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testFindAll() {
        when(locationRepository.findAll()).thenReturn(Arrays.asList(new Location(), new Location()));
        assertEquals(2, locationService.findAll().size());
    }

    @Test
    void testFindById() {
        Location location = new Location();
        when(locationRepository.findById(1)).thenReturn(Optional.of(location));
        assertNotNull(locationService.findById(1));
    }

    @Test
    void testSave() {
        Location location = new Location();
        when(locationRepository.save(location)).thenReturn(location);
        assertEquals(location, locationService.save(location));
    }

    @Test
    void testDeleteById() {
        doNothing().when(locationRepository).deleteById(1);
        locationService.deleteById(1);
        verify(locationRepository, times(1)).deleteById(1);
    }
}