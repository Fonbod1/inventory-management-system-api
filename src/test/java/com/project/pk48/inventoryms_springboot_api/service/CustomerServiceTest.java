package com.project.pk48.inventoryms_springboot_api.service;
import com.project.pk48.inventoryms_springboot_api.models.Customer;
import com.project.pk48.inventoryms_springboot_api.repositories.CustomerRepository;
import com.project.pk48.inventoryms_springboot_api.services.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerServiceTest {

    private CustomerRepository customerRepository;
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        customerRepository = mock(CustomerRepository.class);
        customerService = new CustomerService(customerRepository);
    }

    @Test
    void testGetAllCustomers() {
        when(customerRepository.findAll()).thenReturn(Arrays.asList(new Customer(), new Customer()));
        assertEquals(2, customerService.getAllCustomers().size());
    }

    @Test
    void testGetCustomerById() {
        Customer customer = new Customer();
        customer.setId(1L);
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        assertNotNull(customerService.getCustomerById(1L));
    }

    @Test
    void testSaveCustomer() {
        Customer customer = new Customer();
        when(customerRepository.save(customer)).thenReturn(customer);
        assertEquals(customer, customerService.save(customer));
    }

    @Test
    void testDeleteCustomer() {
        doNothing().when(customerRepository).deleteById(1L);
        customerService.deleteCustomer(1L);
        verify(customerRepository, times(1)).deleteById(1L);
    }
}