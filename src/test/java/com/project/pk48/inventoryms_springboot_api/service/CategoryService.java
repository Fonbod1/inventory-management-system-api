package com.project.pk48.inventoryms_springboot_api.service;

import com.project.pk48.inventoryms_springboot_api.models.Category;
import com.project.pk48.inventoryms_springboot_api.models.CategoryStats;
import com.project.pk48.inventoryms_springboot_api.repositories.CategoryRepository;
import com.project.pk48.inventoryms_springboot_api.services.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryServiceTest {

    private CategoryRepository categoryRepository;
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        categoryRepository = mock(CategoryRepository.class);
        categoryService = new CategoryService(categoryRepository);
    }

    @Test
    void testGetAllCategories() {
        when(categoryRepository.findAll()).thenReturn(Arrays.asList(new Category(), new Category()));
        assertEquals(2, categoryService.getAllCategories().size());
    }

    @Test
    void testGetCategoryById() {
        Category category = new Category();
        category.setId(1L);
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        assertNotNull(categoryService.getCategoryById(1L));
    }

    @Test
    void testSaveCategory() {
        Category category = new Category();
        when(categoryRepository.save(category)).thenReturn(category);
        assertEquals(category, categoryService.save(category));
    }

    @Test
    void testDeleteCategory() {
        doNothing().when(categoryRepository).deleteById(1L);
        categoryService.deleteCategory(1L);
        verify(categoryRepository, times(1)).deleteById(1L);
    }

    @Test
    void testGetCategoryStats() {
        when(categoryRepository.countCategoriesAddedInCurrentMonth(anyInt(), anyInt())).thenReturn(10L);
        when(categoryRepository.countCategoriesAddedInPreviousMonth(anyInt(), anyInt())).thenReturn(5L);
        when(categoryRepository.countCategoriesAddedInDecemberOfPreviousYear(anyInt())).thenReturn(0L);

        CategoryStats stats = categoryService.getCategoryStats();
        assertNotNull(stats);
        assertTrue(stats.getCurrentMonthCount() >= 0);
    }
}