package com.project.pk48.inventoryms_springboot_api.repositories;

import com.project.pk48.inventoryms_springboot_api.models.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @Test
    void testSaveAndFindById() {
        Item item = new Item();
        // Set required fields for Item here, e.g.:
        // item.setName("Sample Item");
        Item saved = itemRepository.save(item);

        Optional<Item> found = itemRepository.findById(saved.getId());
        assertTrue(found.isPresent());
        // assertEquals("Sample Item", found.get().getName());
    }
}