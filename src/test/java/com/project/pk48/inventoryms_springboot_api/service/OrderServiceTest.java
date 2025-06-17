package com.project.pk48.inventoryms_springboot_api.service;

import com.project.pk48.inventoryms_springboot_api.models.Order;
import com.project.pk48.inventoryms_springboot_api.models.OrderStats;
import com.project.pk48.inventoryms_springboot_api.repositories.OrderRepository;
import com.project.pk48.inventoryms_springboot_api.services.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    private OrderRepository orderRepository;
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderRepository = mock(OrderRepository.class);
        orderService = new OrderService(orderRepository);
    }

    @Test
    void testGetAllOrders() {
        when(orderRepository.findAll()).thenReturn(Arrays.asList(new Order(), new Order()));
        assertEquals(2, orderService.getAllOrders().size());
    }

    @Test
    void testGetOrderById() {
        Order order = new Order();
        order.setId(1L);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        assertNotNull(orderService.getOrderById(1L));
    }

    @Test
    void testSave() {
        Order order = new Order();
        when(orderRepository.save(order)).thenReturn(order);
        assertEquals(order, orderService.save(order));
    }

    @Test
    void testDeleteOrder() {
        doNothing().when(orderRepository).deleteById(1L);
        orderService.deleteOrder(1L);
        verify(orderRepository, times(1)).deleteById(1L);
    }

    @Test
    void testGetOrderStats() {
        // Mock current and previous month/year
        when(orderRepository.countOrdersAddedInCurrentMonth(anyInt(), anyInt())).thenReturn(10L);
        when(orderRepository.countOrdersAddedInPreviousMonth(anyInt(), anyInt())).thenReturn(5L);
        when(orderRepository.countOrdersAddedInDecemberOfPreviousYear(anyInt())).thenReturn(3L);

        OrderStats stats = orderService.getOrderStats();
        assertNotNull(stats);
        assertTrue(stats.getCurrentMonthCount() >= 0);
        assertTrue(stats.getPreviousMonthCount() >= 0);
    }
}