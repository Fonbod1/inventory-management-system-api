package com.project.pk48.inventoryms_springboot_api.service;

import com.project.pk48.inventoryms_springboot_api.models.ProductMeta;
import com.project.pk48.inventoryms_springboot_api.repositories.ProductMetaRepository;
import com.project.pk48.inventoryms_springboot_api.services.ProductMetaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductMetaServiceTest {

    private ProductMetaRepository productMetaRepository;
    private ProductMetaService productMetaService;

    @BeforeEach
    void setUp() {
        productMetaRepository = mock(ProductMetaRepository.class);
        productMetaService = new ProductMetaService(productMetaRepository);
    }

    @Test
    void testGetAllProductMetas() {
        when(productMetaRepository.findAll()).thenReturn(Arrays.asList(new ProductMeta(), new ProductMeta()));
        assertEquals(2, productMetaService.getAllProductMetas().size());
    }

    @Test
    void testGetProductMetaById() {
        ProductMeta productMeta = new ProductMeta();
        productMeta.setId(1L);
        when(productMetaRepository.findById(1L)).thenReturn(Optional.of(productMeta));
        assertNotNull(productMetaService.getProductMetaById(1L));
    }

    @Test
    void testSave() {
        ProductMeta productMeta = new ProductMeta();
        when(productMetaRepository.save(productMeta)).thenReturn(productMeta);
        assertEquals(productMeta, productMetaService.save(productMeta));
    }

    @Test
    void testDeleteProductMeta() {
        doNothing().when(productMetaRepository).deleteById(1L);
        productMetaService.deleteProductMeta(1L);
        verify(productMetaRepository, times(1)).deleteById(1L);
    }
}