package com.mpp.pharmacy.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mpp.pharmacy.DTO.PharmacyDTO;
import com.mpp.pharmacy.RequestDTO.PharmacyRequestDTO;
import com.mpp.pharmacy.ServiceInterface.PharmacyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PharmacyController.class)
@AutoConfigureMockMvc(addFilters = false)
public class PharmacyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PharmacyService pharmacyService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createPharmacy_ShouldCreateNewPharmacy() throws Exception {
        PharmacyRequestDTO request = PharmacyRequestDTO.builder()
                .name("HealthPlus Pharmacy")
                .address("123 Main Street, City")
                .phone("+1234567890")
                .build();

        PharmacyDTO createdPharmacy = PharmacyDTO.builder()
                .pharmacyId(1L)
                .name("HealthPlus Pharmacy")
                .address("123 Main Street, City")
                .phone("+1234567890")
                .build();

        when(pharmacyService.create(any(PharmacyRequestDTO.class))).thenReturn(createdPharmacy);

        mockMvc.perform(post("/api/pharmacies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.pharmacyId").value(1))
                .andExpect(jsonPath("$.name").value("HealthPlus Pharmacy"))
                .andExpect(jsonPath("$.address").value("123 Main Street, City"))
                .andExpect(jsonPath("$.phone").value("+1234567890"));
    }

    @Test
    void getById_ShouldReturnASpecificPharmacy() throws Exception {
        PharmacyDTO pharmacy = PharmacyDTO.builder()
                .pharmacyId(1L)
                .name("Test Pharmacy")
                .address("123 Main Street, City")
                .phone("+1234567890")
                .build();

        when(pharmacyService.getById(1L)).thenReturn(pharmacy);

        mockMvc.perform(get("/api/pharmacies/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pharmacyId").value(1L))
                .andExpect(jsonPath("$.name").value("Test Pharmacy"))
                .andExpect(jsonPath("$.address").value("123 Main Street, City"))
                .andExpect(jsonPath("$.phone").value("+1234567890"));
    }

    @Test
    void getAll_ShouldReturnAllPharmacies() throws Exception {
        PharmacyDTO pharmacy1 = PharmacyDTO.builder()
                .pharmacyId(1L)
                .name("Pharmacy One")
                .address("123 Main Street, City")
                .phone("+1234567890")
                .build();

        PharmacyDTO pharmacy2 = PharmacyDTO.builder()
                .pharmacyId(2L)
                .name("CareWell Pharmacy")
                .address("456 Oak Avenue, Suburb, City")
                .phone("+9876543210")
                .build();

        PharmacyDTO pharmacy3 = PharmacyDTO.builder()
                .pharmacyId(3L)
                .name("MediCare & Co.")
                .address("789 Pine Road, Uptown, City")
                .phone("+1122334455")
                .build();

        when(pharmacyService.getAll()).thenReturn(List.of(pharmacy1, pharmacy2, pharmacy3));

        // Act & Assert
        mockMvc.perform(get("/api/pharmacies"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(3))
                .andExpect(jsonPath("$[0].pharmacyId").value(1))
                .andExpect(jsonPath("$[0].name").value("Pharmacy One"))
                .andExpect(jsonPath("$[0].address").value("123 Main Street, City"))
                .andExpect(jsonPath("$[1].pharmacyId").value(2))
                .andExpect(jsonPath("$[1].name").value("CareWell Pharmacy"))
                .andExpect(jsonPath("$[1].phone").value("+9876543210"))
                .andExpect(jsonPath("$[2].pharmacyId").value(3))
                .andExpect(jsonPath("$[2].name").value("MediCare & Co."));
    }

    @Test
    void update_ShouldReturnUpdatedPharmacy() throws Exception {
        // Arrange
        Long pharmacyId = 1L;

        PharmacyRequestDTO request = PharmacyRequestDTO.builder()
                .name("HealthPlus Pharmacy - Updated")
                .address("123 Main Street, Suite 200, Downtown, City")
                .phone("+1234567899")
                .build();

        PharmacyDTO response = PharmacyDTO.builder()
                .pharmacyId(pharmacyId)
                .name("HealthPlus Pharmacy - Updated")
                .address("123 Main Street, Suite 200, Downtown, City")
                .phone("+1234567899")
                .build();

        when(pharmacyService.update(eq(pharmacyId), any(PharmacyRequestDTO.class))).thenReturn(response);

        // Act & Assert
        mockMvc.perform(put("/api/pharmacies/{id}", pharmacyId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.pharmacyId").value(1))
                .andExpect(jsonPath("$.name").value("HealthPlus Pharmacy - Updated"))
                .andExpect(jsonPath("$.address").value("123 Main Street, Suite 200, Downtown, City"))
                .andExpect(jsonPath("$.phone").value("+1234567899"));
    }

    @Test
    void delete_ShouldReturnNoContent() throws Exception {
        // Arrange
        Long pharmacyId = 1L;
        doNothing().when(pharmacyService).delete(eq(pharmacyId));

        // Act & Assert
        mockMvc.perform(delete("/api/pharmacies/{id}", pharmacyId))
                .andExpect(status().isNoContent());
    }
}
