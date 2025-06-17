package com.project.pk48.inventoryms_springboot_api.service;

import com.project.pk48.inventoryms_springboot_api.models.OrderItem;
import com.project.pk48.inventoryms_springboot_api.repositories.OrderItemRepository;
import com.project.pk48.inventoryms_springboot_api.services.OrderItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderItemServiceTest {

    private OrderItemRepository orderItemRepository;
    private OrderItemService orderItemService;

    @BeforeEach
    void setUp() {
        orderItemRepository = mock(OrderItemRepository.class);
        orderItemService = new OrderItemService(orderItemRepository);
    }

    @Test
    void testGetAllOrderItems() {
        when(orderItemRepository.findAll()).thenReturn(Arrays.asList(new OrderItem(), new OrderItem()));
        assertEquals(2, orderItemService.getAllOrderItems().size());
    }

    @Test
    void testGetOrderItemById() {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(1L);
        when(orderItemRepository.findById(1L)).thenReturn(Optional.of(orderItem));
        assertNotNull(orderItemService.getOrderItemById(1L));
    }

    @Test
    void testSave() {
        OrderItem orderItem = new OrderItem();
        when(orderItemRepository.save(orderItem)).thenReturn(orderItem);
        assertEquals(orderItem, orderItemService.save(orderItem));
    }

    @Test
    void testDeleteOrderItem() {
        doNothing().when(orderItemRepository).deleteById(1L);
        orderItemService.deleteOrderItem(1L);
        verify(orderItemRepository, times(1)).deleteById(1L);
    }
}