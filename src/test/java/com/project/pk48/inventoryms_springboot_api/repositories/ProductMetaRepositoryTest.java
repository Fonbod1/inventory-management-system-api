package com.project.pk48.inventoryms_springboot_api.repositories;

import com.project.pk48.inventoryms_springboot_api.models.ProductMeta;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ProductMetaRepositoryTest {

    @Autowired
    private ProductMetaRepository productMetaRepository;

    @Test
    void testSaveAndFindById() {
        ProductMeta productMeta = new ProductMeta();
        // Set required fields for ProductMeta here, e.g.:
        // productMeta.setKey("color");
        // productMeta.setValue("red");
        ProductMeta saved = productMetaRepository.save(productMeta);

        Optional<ProductMeta> found = productMetaRepository.findById(saved.getId());
        assertTrue(found.isPresent());
        // assertEquals("color", found.get().getKey());
        // assertEquals("red", found.get().getValue());
    }
}