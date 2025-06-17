package com.project.pk48.inventoryms_springboot_api.repositories;

import com.project.pk48.inventoryms_springboot_api.models.Supplier;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class SupplierRepositoryTest {

    @Autowired
    private SupplierRepository supplierRepository;

    @Test
    void testSaveAndFindById() {
        Supplier supplier = new Supplier();
        supplier.setName("Acme Supplies"); // Set other required fields if needed

        Supplier saved = supplierRepository.save(supplier);

        Optional<Supplier> found = supplierRepository.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals("Acme Supplies", found.get().getName());
    }
}