package com.mpp.pharmacy.Controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mpp.pharmacy.DTO.PrescriptionDTO;
import com.mpp.pharmacy.DTO.PrescriptionDetailDTO;
import com.mpp.pharmacy.RequestDTO.PrescriptionDetailRequestDTO;
import com.mpp.pharmacy.RequestDTO.PrescriptionRequestDTO;
import com.mpp.pharmacy.Services.PrescriptionDetailServiceImpl;
import com.mpp.pharmacy.Services.PrescriptionServiceImpl;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.any;
import java.util.List;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PrescriptionController.class)
@AutoConfigureMockMvc(addFilters = false)
class PrescriptionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PrescriptionServiceImpl prescriptionService;

    @MockBean
    private PrescriptionDetailServiceImpl prescriptionDetailService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllPrescription_ShouldReturnList() throws Exception {
        PrescriptionDTO prescription = new PrescriptionDTO();

        when(prescriptionService.getAll()).thenReturn(List.of(prescription));

        mockMvc.perform(get("/api/prescriptions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    void createPrescription_ShouldReturnCreated() throws Exception {
        PrescriptionRequestDTO request = new PrescriptionRequestDTO();

        PrescriptionDTO response = new PrescriptionDTO();

        when(prescriptionService.create(any(PrescriptionRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/prescriptions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void getPrescription_ShouldReturnOne() throws Exception {
        Long prescriptionId = 1L;
        PrescriptionDTO response = new PrescriptionDTO();

        when(prescriptionService.getById(eq(prescriptionId))).thenReturn(response);

        mockMvc.perform(get("/api/prescriptions/{id}", prescriptionId))
                .andExpect(status().isOk());
    }

    @Test
    void deletePrescription_ShouldReturnNoContent() throws Exception {
        Long prescriptionId = 1L;

        mockMvc.perform(delete("/api/prescriptions/{id}", prescriptionId))
                .andExpect(status().isNoContent());
    }

    @Test
    void updatePrescription_ShouldReturnUpdated() throws Exception {
        Long prescriptionId = 1L;
        PrescriptionRequestDTO request = new PrescriptionRequestDTO();
        PrescriptionDTO response = new PrescriptionDTO();

        when(prescriptionService.update(eq(prescriptionId), any(PrescriptionRequestDTO.class))).thenReturn(response);

        mockMvc.perform(put("/api/prescriptions/{id}", prescriptionId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void createPrescriptionDetail_ShouldReturnCreated() throws Exception {
        PrescriptionDetailRequestDTO request = new PrescriptionDetailRequestDTO();
        PrescriptionDetailDTO response = new PrescriptionDetailDTO();

        when(prescriptionDetailService.create(any(PrescriptionDetailRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/prescription-details")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void getAllPrescriptionDetail_ShouldReturnList() throws Exception {
        PrescriptionDetailDTO prescriptionDetail = new PrescriptionDetailDTO();

        when(prescriptionDetailService.getAll()).thenReturn(List.of(prescriptionDetail));

        mockMvc.perform(get("/api/prescription-details"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    void getPrescriptionDetailByIds_ShouldReturnPrescriptionDetailDTO() throws Exception {
        Long prescriptionId = 1L;
        Long drugId = 1L;
        PrescriptionDetailDTO response = new PrescriptionDetailDTO();

        when(prescriptionDetailService.get(eq(prescriptionId), eq(drugId))).thenReturn(response);

        mockMvc.perform(get("/api/prescription-details/{prescriptionId}/{drugId}", prescriptionId, drugId))
                .andExpect(status().isOk());
    }

    @Test
    void getDetailsByPrescription_ShouldReturnList() throws Exception {
        Long prescriptionId = 1L;

        PrescriptionDetailDTO prescriptionDetail = new PrescriptionDetailDTO();

        when(prescriptionDetailService.getAll()).thenReturn(List.of(prescriptionDetail));

        mockMvc.perform(get("/api/prescriptions/{prescriptionId}/details", prescriptionId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    void updatePrescriptionDetail_ShouldReturnUpdated() throws Exception {
        Long prescriptionId = 1L;
        Long drugId = 1L;
        PrescriptionDetailRequestDTO request = new PrescriptionDetailRequestDTO();
        PrescriptionDetailDTO response = new PrescriptionDetailDTO();

        when(prescriptionDetailService.update(
                eq(prescriptionId),
                eq(drugId),
                any(PrescriptionDetailRequestDTO.class)
        )).thenReturn(response);

        mockMvc.perform(put("/api/prescription-details/{prescriptionId}/{drugId}", prescriptionId, drugId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void deletePrescriptionDetail_ShouldReturnNoContent() throws Exception {
        Long prescriptionId = 1L;
        Long drugId = 1L;

        mockMvc.perform(delete("/api/prescription-details/{prescriptionId}/{drugId}", prescriptionId, drugId))
                .andExpect(status().isNoContent());
    }
}