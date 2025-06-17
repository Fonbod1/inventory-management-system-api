package com.project.pk48.inventoryms_springboot_api.service;

import com.project.pk48.inventoryms_springboot_api.models.Item;
import com.project.pk48.inventoryms_springboot_api.repositories.ItemRepository;
import com.project.pk48.inventoryms_springboot_api.services.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ItemServiceTest {

    private ItemRepository itemRepository;
    private ItemService itemService;

    @BeforeEach
    void setUp() {
        itemRepository = mock(ItemRepository.class);
        itemService = new ItemService(itemRepository);
    }

    @Test
    void testGetAllItems() {
        when(itemRepository.findAll()).thenReturn(Arrays.asList(new Item(), new Item()));
        assertEquals(2, itemService.getAllItems().size());
    }

    @Test
    void testGetItemById() {
        Item item = new Item();
        item.setId(1L);
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        assertNotNull(itemService.getItemById(1L));
    }

    @Test
    void testSave() {
        Item item = new Item();
        when(itemRepository.save(item)).thenReturn(item);
        assertEquals(item, itemService.save(item));
    }

    @Test
    void testDeleteItem() {
        doNothing().when(itemRepository).deleteById(1L);
        itemService.deleteItem(1L);
        verify(itemRepository, times(1)).deleteById(1L);
    }
}