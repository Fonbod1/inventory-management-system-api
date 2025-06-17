package com.project.pk48.inventoryms_springboot_api.repositories;
// src/test/java/com/project/pk48/inventoryms_springboot_api/repositories/BrandRepositoryTest.java


import com.project.pk48.inventoryms_springboot_api.models.Brand;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BrandRepositoryTest {

    @Autowired
    private BrandRepository brandRepository;

    @Test
    void testSaveAndFindBrand() {
        Brand brand = new Brand();
        brand.setTitle("Test Brand");
        brand.setCreatedAt(new java.util.Date());
        Brand saved = brandRepository.save(brand);
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getTitle()).isEqualTo("Test Brand");
    }
}