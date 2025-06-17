package com.project.pk48.inventoryms_springboot_api.service;
import com.project.pk48.inventoryms_springboot_api.models.CommonObject;
import com.project.pk48.inventoryms_springboot_api.repositories.CommonObjectRepository;
import com.project.pk48.inventoryms_springboot_api.services.CommonObjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommonObjectServiceTest {

    private CommonObjectRepository commonObjectRepository;
    private CommonObjectService commonObjectService;

    @BeforeEach
    void setUp() {
        commonObjectRepository = mock(CommonObjectRepository.class);
        commonObjectService = new CommonObjectService(commonObjectRepository);
    }

    @Test
    void testGetAllCommonObjects() {
        when(commonObjectRepository.findAll()).thenReturn(Arrays.asList(new CommonObject(), new CommonObject()));
        assertEquals(2, commonObjectService.getAllCommonObjects().size());
    }

    @Test
    void testFindByObjectType() {
        when(commonObjectRepository.findByObjectType("typeA")).thenReturn(Arrays.asList(new CommonObject()));
        assertEquals(1, commonObjectService.findByObjectType("typeA").size());
    }

    @Test
    void testGetCommonObjectById() {
        CommonObject obj = new CommonObject();
        obj.setId(1L);
        when(commonObjectRepository.findById(1L)).thenReturn(Optional.of(obj));
        assertNotNull(commonObjectService.getCommonObjectById(1L));
    }

    @Test
    void testSave() {
        CommonObject obj = new CommonObject();
        when(commonObjectRepository.save(obj)).thenReturn(obj);
        assertEquals(obj, commonObjectService.save(obj));
    }

    @Test
    void testDeleteCommonObject() {
        doNothing().when(commonObjectRepository).deleteById(1L);
        commonObjectService.deleteCommonObject(1L);
        verify(commonObjectRepository, times(1)).deleteById(1L);
    }
}