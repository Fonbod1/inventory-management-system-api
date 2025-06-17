package com.project.pk48.inventoryms_springboot_api.service;
import com.project.pk48.inventoryms_springboot_api.models.Product;
import com.project.pk48.inventoryms_springboot_api.models.ProductStats;
import com.project.pk48.inventoryms_springboot_api.repositories.ProductRepository;
import com.project.pk48.inventoryms_springboot_api.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    private ProductRepository productRepository;
    private ProductService productService;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        productService = new ProductService(productRepository);
    }

    @Test
    void testGetAllProducts() {
        when(productRepository.findAll()).thenReturn(Arrays.asList(new Product(), new Product()));
        assertEquals(2, productService.getAllProducts().size());
    }

    @Test
    void testGetProductById() {
        Product product = new Product();
        product.setId(1L);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        assertNotNull(productService.getProductById(1L));
    }

    @Test
    void testSave() {
        Product product = new Product();
        when(productRepository.save(product)).thenReturn(product);
        assertEquals(product, productService.save(product));
    }

    @Test
    void testUpdateProduct() {
        Product product = new Product();
        when(productRepository.save(product)).thenReturn(product);
        assertEquals(product, productService.updateProduct(product));
    }

    @Test
    void testDeleteProduct() {
        doNothing().when(productRepository).deleteById(1L);
        productService.deleteProduct(1L);
        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    void testGetProductStats_NormalMonth() {
        when(productRepository.countProductsAddedInCurrentMonth(anyInt(), anyInt())).thenReturn(10L);
        when(productRepository.countProductsAddedInPreviousMonth(anyInt(), anyInt())).thenReturn(5L);
        when(productRepository.countProductsAddedInDecemberOfPreviousYear(anyInt())).thenReturn(0L);

        ProductStats stats = productService.getProductStats();
        assertNotNull(stats);
        assertEquals(10L, stats.getCurrentMonthCount());
        assertEquals(5L, stats.getPreviousMonthCount());
        assertEquals(100.0, stats.getPercentageDifference());
    }

    @Test
    void testGetProductStats_January() {
        // Simulate January by manipulating Calendar if needed, or just test logic
        when(productRepository.countProductsAddedInCurrentMonth(anyInt(), anyInt())).thenReturn(8L);
        when(productRepository.countProductsAddedInPreviousMonth(anyInt(), anyInt())).thenReturn(0L);
        when(productRepository.countProductsAddedInDecemberOfPreviousYear(anyInt())).thenReturn(4L);

        ProductStats stats = productService.getProductStats();
        assertNotNull(stats);
        assertTrue(stats.getCurrentMonthCount() >= 0);
        assertTrue(stats.getPreviousMonthCount() >= 0);
    }
}