package com.project.pk48.inventoryms_springboot_api.service;

import com.project.pk48.inventoryms_springboot_api.models.Supplier;
import com.project.pk48.inventoryms_springboot_api.repositories.SupplierRepository;
import com.project.pk48.inventoryms_springboot_api.services.SupplierService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SupplierServiceTest {

    private SupplierRepository supplierRepository;
    private SupplierService supplierService;

    @BeforeEach
    void setUp() {
        supplierRepository = mock(SupplierRepository.class);
        supplierService = new SupplierService();
        // Use reflection to inject the mock (since field is private and @Autowired)
        java.lang.reflect.Field field;
        try {
            field = SupplierService.class.getDeclaredField("supplierRepository");
            field.setAccessible(true);
            field.set(supplierService, supplierRepository);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testFindAll() {
        when(supplierRepository.findAll()).thenReturn(Arrays.asList(new Supplier(), new Supplier()));
        assertEquals(2, supplierService.findAll().size());
    }

    @Test
    void testFindById() {
        Supplier supplier = new Supplier();
        supplier.setId(1L);
        when(supplierRepository.findById(1L)).thenReturn(Optional.of(supplier));
        assertNotNull(supplierService.findById(1L));
    }

    @Test
    void testSave() {
        Supplier supplier = new Supplier();
        when(supplierRepository.save(supplier)).thenReturn(supplier);
        assertEquals(supplier, supplierService.save(supplier));
    }

    @Test
    void testDeleteById() {
        doNothing().when(supplierRepository).deleteById(1L);
        supplierService.deleteById(1L);
        verify(supplierRepository, times(1)).deleteById(1L);
    }
}
