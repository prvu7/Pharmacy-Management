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
                .name("Test Pharmacy")
                .address("123 Main Street, City")
                .phone("+1234567890")
                .build();

        PharmacyDTO createdPharmacy = PharmacyDTO.builder()
                .pharmacyId(1L)
                .name("Test Pharmacy")
                .address("123 Main Street, City")
                .phone("+1234567890")
                .build();

        when(pharmacyService.create(any(PharmacyRequestDTO.class))).thenReturn(createdPharmacy);

        mockMvc.perform(post("/api/pharmacies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pharmacyId").value(1L))
                .andExpect(jsonPath("$.name").value("Test Pharmacy"))
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
                .name("Pharmacy Two")
                .address("456 Second Avenue, Town")
                .phone("+0987654321")
                .build();

        List<PharmacyDTO> pharmacies = Arrays.asList(pharmacy1, pharmacy2);

        when(pharmacyService.getAll()).thenReturn(pharmacies);

        mockMvc.perform(get("/api/pharmacies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].pharmacyId").value(1L))
                .andExpect(jsonPath("$[0].name").value("Pharmacy One"))
                .andExpect(jsonPath("$[1].pharmacyId").value(2L))
                .andExpect(jsonPath("$[1].name").value("Pharmacy Two"));
    }

    @Test
    void update_ShouldUpdateExistingPharmacy() throws Exception {
        PharmacyRequestDTO request = PharmacyRequestDTO.builder()
                .name("Updated Pharmacy")
                .address("789 Updated Street, City")
                .phone("+1112223333")
                .build();

        PharmacyDTO updatedPharmacy = PharmacyDTO.builder()
                .pharmacyId(1L)
                .name("Updated Pharmacy")
                .address("789 Updated Street, City")
                .phone("+1112223333")
                .build();

        when(pharmacyService.update(eq(1L), any(PharmacyRequestDTO.class))).thenReturn(updatedPharmacy);

        mockMvc.perform(put("/api/pharmacies/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pharmacyId").value(1L))
                .andExpect(jsonPath("$.name").value("Updated Pharmacy"))
                .andExpect(jsonPath("$.address").value("789 Updated Street, City"))
                .andExpect(jsonPath("$.phone").value("+1112223333"));
    }

    @Test
    void delete_ShouldDeletePharmacy() throws Exception {
        doNothing().when(pharmacyService).delete(1L);

        mockMvc.perform(delete("/api/pharmacies/1"))
                .andExpect(status().isNoContent());
    }
}
