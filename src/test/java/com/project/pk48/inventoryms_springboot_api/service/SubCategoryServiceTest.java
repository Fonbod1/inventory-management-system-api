package com.project.pk48.inventoryms_springboot_api.service;

import com.project.pk48.inventoryms_springboot_api.models.SubCategory;
import com.project.pk48.inventoryms_springboot_api.repositories.SubCategoryRepository;
import com.project.pk48.inventoryms_springboot_api.services.SubCategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SubCategoryServiceTest {

    private SubCategoryRepository subCategoryRepository;
    private SubCategoryService subCategoryService;

    @BeforeEach
    void setUp() {
        subCategoryRepository = mock(SubCategoryRepository.class);
        subCategoryService = new SubCategoryService(subCategoryRepository);
    }

    @Test
    void testGetAllSubCategories() {
        when(subCategoryRepository.findAll()).thenReturn(Arrays.asList(new SubCategory(), new SubCategory()));
        assertEquals(2, subCategoryService.getAllSubCategories().size());
    }

    @Test
    void testGetSubCategoryById() {
        SubCategory subCategory = new SubCategory();
        subCategory.setId(1L);
        when(subCategoryRepository.findById(1L)).thenReturn(Optional.of(subCategory));
        assertNotNull(subCategoryService.getSubCategoryById(1L));
    }

    @Test
    void testSave() {
        SubCategory subCategory = new SubCategory();
        when(subCategoryRepository.save(subCategory)).thenReturn(subCategory);
        assertEquals(subCategory, subCategoryService.save(subCategory));
    }

    @Test
    void testDeleteSubCategory() {
        doNothing().when(subCategoryRepository).deleteById(1L);
        subCategoryService.deleteSubCategory(1L);
        verify(subCategoryRepository, times(1)).deleteById(1L);
    }
}