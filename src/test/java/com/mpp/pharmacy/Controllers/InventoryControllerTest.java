package com.mpp.pharmacy.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mpp.pharmacy.DTO.InventoryDTO;
import com.mpp.pharmacy.RequestDTO.InventoryRequestDTO;
import com.mpp.pharmacy.ServiceInterface.InventoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InventoryController.class)
@AutoConfigureMockMvc(addFilters = false)
public class InventoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private InventoryService inventoryService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void create_ShouldReturnCreatedInventory() throws Exception {
        // Arrange
        InventoryRequestDTO request = InventoryRequestDTO.builder()
                .pharmacyId(10L)
                .drugId(20L)
                .quantityInStock(100)
                .expiryDate(LocalDate.of(2025, 12, 31))
                .build();

        InventoryDTO response = InventoryDTO.builder()
                .inventoryId(1L)
                .pharmacyId(10L)
                .drugId(20L)
                .quantityInStock(100)
                .expiryDate(LocalDate.of(2025, 12, 31))
                .build();

        when(inventoryService.create(any(InventoryRequestDTO.class))).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/api/inventory")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.inventoryId").value(1))
                .andExpect(jsonPath("$.pharmacyId").value(10))
                .andExpect(jsonPath("$.drugId").value(20))
                .andExpect(jsonPath("$.quantityInStock").value(100));
    }

    @Test
    void getInventory_ShouldReturnListOfInventory() throws Exception {
        // Arrange
        InventoryDTO inventory1 = InventoryDTO.builder()
                .inventoryId(1L)
                .pharmacyId(10L)
                .drugId(20L)
                .quantityInStock(100)
                .expiryDate(LocalDate.of(2025, 12, 31))
                .build();

        InventoryDTO inventory2 = InventoryDTO.builder()
                .inventoryId(2L)
                .pharmacyId(15L)
                .drugId(25L)
                .quantityInStock(50)
                .expiryDate(LocalDate.of(2026, 6, 30))
                .build();

        when(inventoryService.getAll()).thenReturn(List.of(inventory1, inventory2));

        // Act & Assert
        mockMvc.perform(get("/api/inventory"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].inventoryId").value(1))
                .andExpect(jsonPath("$[0].pharmacyId").value(10))
                .andExpect(jsonPath("$[1].inventoryId").value(2))
                .andExpect(jsonPath("$[1].pharmacyId").value(15));
    }

    @Test
    void getInventoryByPharmacy_ShouldReturnInventoryForSpecificPharmacy() throws Exception {
        // Arrange
        Long pharmacyId = 10L;

        InventoryDTO inventory1 = InventoryDTO.builder()
                .inventoryId(1L)
                .pharmacyId(pharmacyId)
                .drugId(20L)
                .quantityInStock(100)
                .expiryDate(LocalDate.of(2025, 12, 31))
                .build();

        InventoryDTO inventory2 = InventoryDTO.builder()
                .inventoryId(3L)
                .pharmacyId(pharmacyId)
                .drugId(30L)
                .quantityInStock(75)
                .expiryDate(LocalDate.of(2026, 3, 15))
                .build();

        when(inventoryService.getByPharmacy(eq(pharmacyId))).thenReturn(List.of(inventory1, inventory2));

        // Act & Assert
        mockMvc.perform(get("/api/inventory/pharmacy/{pharmacyId}", pharmacyId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].inventoryId").value(1))
                .andExpect(jsonPath("$[0].pharmacyId").value(10))
                .andExpect(jsonPath("$[0].drugId").value(20))
                .andExpect(jsonPath("$[1].inventoryId").value(3))
                .andExpect(jsonPath("$[1].pharmacyId").value(10))
                .andExpect(jsonPath("$[1].drugId").value(30));
    }


}
