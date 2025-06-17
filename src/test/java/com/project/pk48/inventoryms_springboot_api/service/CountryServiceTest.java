package com.project.pk48.inventoryms_springboot_api.service;

import com.project.pk48.inventoryms_springboot_api.models.Country;
import com.project.pk48.inventoryms_springboot_api.repositories.CountryRepository;
import com.project.pk48.inventoryms_springboot_api.services.CountryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CountryServiceTest {

    private CountryRepository countryRepository;
    private CountryService countryService;

    @BeforeEach
    void setUp() {
        countryRepository = mock(CountryRepository.class);
        countryService = new CountryService();
        // Use reflection to inject the mock (since @Autowired is used in the service)
        java.lang.reflect.Field field;
        try {
            field = CountryService.class.getDeclaredField("countryRepository");
            field.setAccessible(true);
            field.set(countryService, countryRepository);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testFindAll() {
        when(countryRepository.findAll()).thenReturn(Arrays.asList(new Country(), new Country()));
        assertEquals(2, countryService.findAll().size());
    }

    @Test
    void testGetCountry() {
        Country country = new Country();
        when(countryRepository.findById(1)).thenReturn(Optional.of(country));
        assertNotNull(countryService.getCountry(1));
    }

    @Test
    void testSave() {
        Country country = new Country();
        when(countryRepository.save(country)).thenReturn(country);
        assertEquals(country, countryService.save(country));
    }

    @Test
    void testDelete() {
        doNothing().when(countryRepository).deleteById(1);
        countryService.delete(1);
        verify(countryRepository, times(1)).deleteById(1);
    }

    @Test
    void testGetById() {
        Country country = new Country();
        when(countryRepository.findById(1)).thenReturn(Optional.of(country));
        assertNotNull(countryService.getById(1));
    }

    @Test
    void testUpdate() {
        Country country = new Country();
        when(countryRepository.save(country)).thenReturn(country);
        countryService.update(country);
        verify(countryRepository, times(1)).save(country);
    }
}