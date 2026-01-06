package com.mpp.pharmacy.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mpp.pharmacy.DTO.TreatmentDTO;
import com.mpp.pharmacy.RequestDTO.TreatmentRequestDTO;
import com.mpp.pharmacy.ServiceInterface.TreatmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(TreatmentController.class)
@AutoConfigureMockMvc(addFilters = false)
public class TreatmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TreatmentService treatmentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getByTreatmentId_ShouldReturnASpecificTreatment() throws Exception {
        // Arrange
        Long treatmentId = 1L;
        TreatmentDTO treatment = TreatmentDTO.builder()
                .treatmentId(treatmentId)
                .treatmentName("Physical Therapy")
                .description("Weekly physical therapy sessions")
                .doctorId(5L)
                .startDate(java.time.LocalDate.of(2025, 1, 1))
                .endDate(java.time.LocalDate.of(2025, 3, 31))
                .build();

        when(treatmentService.getById(eq(treatmentId))).thenReturn(treatment);

        // Act & Assert
        mockMvc.perform(get("/api/treatment/{id}", treatmentId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.treatmentId").value(1))
                .andExpect(jsonPath("$.treatmentName").value("Physical Therapy"))
                .andExpect(jsonPath("$.doctorId").value(5));
    }

    @Test
    void update_ShouldReturnUpdatedTreatment() throws Exception {
        // Arrange
        Long treatmentId = 1L;
        TreatmentRequestDTO request = TreatmentRequestDTO.builder()
                .treatmentName("Updated Physical Therapy")
                .description("Modified therapy sessions - twice a week")
                .doctorId(5L)
                .startDate(java.time.LocalDate.of(2025, 1, 1))
                .endDate(java.time.LocalDate.of(2025, 6, 30))
                .build();

        TreatmentDTO updatedTreatment = TreatmentDTO.builder()
                .treatmentId(treatmentId)
                .treatmentName("Updated Physical Therapy")
                .description("Modified therapy sessions - twice a week")
                .doctorId(5L)
                .startDate(java.time.LocalDate.of(2025, 1, 1))
                .endDate(java.time.LocalDate.of(2025, 6, 30))
                .build();

        when(treatmentService.update(eq(treatmentId), any(TreatmentRequestDTO.class))).thenReturn(updatedTreatment);

        // Act & Assert
        mockMvc.perform(put("/api/treatment/{id}", treatmentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.treatmentId").value(1))
                .andExpect(jsonPath("$.treatmentName").value("Updated Physical Therapy"))
                .andExpect(jsonPath("$.description").value("Modified therapy sessions - twice a week"))
                .andExpect(jsonPath("$.doctorId").value(5));
    }
}
