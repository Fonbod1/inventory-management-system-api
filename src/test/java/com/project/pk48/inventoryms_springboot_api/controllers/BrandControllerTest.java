package com.project.pk48.inventoryms_springboot_api.controllers;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.pk48.inventoryms_springboot_api.models.Brand;
import com.project.pk48.inventoryms_springboot_api.models.BrandStats;
import com.project.pk48.inventoryms_springboot_api.services.BrandService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class BrandControllerTest {

    private MockMvc mockMvc;
    private BrandService brandService;

    @BeforeEach
    void setUp() {
        brandService = mock(BrandService.class);
        BrandController brandController = new BrandController(brandService);
        mockMvc = MockMvcBuilders.standaloneSetup(brandController).build();
    }

    @Test
    void testGetBrands() throws Exception {
        Brand brand = new Brand();
        brand.setId(1L);
        brand.setTitle("TestBrand");
        when(brandService.getAllBrands()).thenReturn(Arrays.asList(brand));

        mockMvc.perform(get("/brands"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("TestBrand"));
    }

    @Test
    void testGetBrand() throws Exception {
        Brand brand = new Brand();
        brand.setId(1L);
        brand.setTitle("TestBrand");
        when(brandService.getBrandById(1L)).thenReturn(brand);

        mockMvc.perform(get("/brand/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("TestBrand"));
    }

    @Test
    void testAddNew() throws Exception {
        Brand brand = new Brand();
        brand.setTitle("newTestBrand");
        Brand saved = new Brand();
        saved.setId(2L);
        brand.setTitle("newTestBrand");
        when(brandService.save(ArgumentMatchers.any(Brand.class))).thenReturn(saved);

        mockMvc.perform(post("/brands")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(brand)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("NewBrand"));
    }

    @Test
    void testUpdateBrand() throws Exception {
        Brand brand = new Brand();
        brand.setId(1L);
        brand.setTitle("UpdatedBrand");
        when(brandService.save(ArgumentMatchers.any(Brand.class))).thenReturn(brand);

        mockMvc.perform(put("/brand/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(brand)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("UpdatedBrand"));
    }

    @Test
    void testDeleteBrand() throws Exception {
        doNothing().when(brandService).deleteBrand(1L);

        mockMvc.perform(delete("/brand/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetBrandStats() throws Exception {
        BrandStats stats = new BrandStats(5L, 3L, 66.7);
        when(brandService.getBrandStats()).thenReturn(stats);

        mockMvc.perform(get("/brand/stats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currentMonthCount").value(5))
                .andExpect(jsonPath("$.previousMonthCount").value(3))
                .andExpect(jsonPath("$.percentageDifference").value(66.7));
    }
}