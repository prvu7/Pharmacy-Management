package com.mpp.pharmacy.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mpp.pharmacy.DTO.PurchaseDTO;
import com.mpp.pharmacy.RequestDTO.PurchaseRequestDTO;
import com.mpp.pharmacy.ServiceInterface.PurchaseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;

@WebMvcTest(PurchaseController.class)
@AutoConfigureMockMvc(addFilters = false)
public class PurchaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PurchaseService purchaseService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createPurchase_ShouldReturnCreatedPurchase() throws Exception {
        // Arrange
        PurchaseRequestDTO request = PurchaseRequestDTO.builder()
                .pharmacyId(1L)
                .patientId(2L)
                .prescriptionId(3L)
                .purchaseDate(LocalDate.of(2026, 1, 10))
                .totalAmount(BigDecimal.valueOf(150.75))
                .build();

        PurchaseDTO createdPurchase = PurchaseDTO.builder()
                .purchaseId(1L)
                .pharmacyId(1L)
                .patientId(2L)
                .prescriptionId(3L)
                .purchaseDate(LocalDate.of(2026, 1, 10))
                .totalAmount(BigDecimal.valueOf(150.75))
                .build();

        when(purchaseService.create(any(PurchaseRequestDTO.class))).thenReturn(createdPurchase);

        // Act & Assert
        mockMvc.perform(post("/api/purchases")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.purchaseId", is(1)))
                .andExpect(jsonPath("$.pharmacyId", is(1)))
                .andExpect(jsonPath("$.patientId", is(2)))
                .andExpect(jsonPath("$.prescriptionId", is(3)))
                .andExpect(jsonPath("$.purchaseDate", is("2026-01-10")))
                .andExpect(jsonPath("$.totalAmount", is(150.75)));
    }

    @Test
    void getPurchaseById_ShouldReturnPurchase() throws Exception {
        // Arrange
        Long purchaseId = 1L;
        PurchaseDTO purchase = PurchaseDTO.builder()
                .purchaseId(purchaseId)
                .pharmacyId(1L)
                .patientId(2L)
                .prescriptionId(3L)
                .purchaseDate(LocalDate.of(2026, 1, 10))
                .totalAmount(BigDecimal.valueOf(150.75))
                .build();

        when(purchaseService.getById(purchaseId)).thenReturn(purchase);

        // Act & Assert
        mockMvc.perform(get("/api/purchases/{id}", purchaseId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.purchaseId", is(1)))
                .andExpect(jsonPath("$.pharmacyId", is(1)))
                .andExpect(jsonPath("$.patientId", is(2)))
                .andExpect(jsonPath("$.prescriptionId", is(3)))
                .andExpect(jsonPath("$.purchaseDate", is("2026-01-10")))
                .andExpect(jsonPath("$.totalAmount", is(150.75)));
    }

    @Test
    void getAllPurchases_ShouldReturnListOfPurchases() throws Exception {
        // Arrange
        PurchaseDTO purchase1 = PurchaseDTO.builder()
                .purchaseId(1L)
                .pharmacyId(1L)
                .patientId(2L)
                .prescriptionId(3L)
                .purchaseDate(LocalDate.of(2026, 1, 10))
                .totalAmount(BigDecimal.valueOf(150.75))
                .build();

        PurchaseDTO purchase2 = PurchaseDTO.builder()
                .purchaseId(2L)
                .pharmacyId(2L)
                .patientId(3L)
                .prescriptionId(4L)
                .purchaseDate(LocalDate.of(2026, 1, 11))
                .totalAmount(BigDecimal.valueOf(200.50))
                .build();

        List<PurchaseDTO> purchases = Arrays.asList(purchase1, purchase2);
        when(purchaseService.getAll()).thenReturn(purchases);

        // Act & Assert
        mockMvc.perform(get("/api/purchases"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].purchaseId", is(1)))
                .andExpect(jsonPath("$[0].pharmacyId", is(1)))
                .andExpect(jsonPath("$[0].patientId", is(2)))
                .andExpect(jsonPath("$[0].totalAmount", is(150.75)))
                .andExpect(jsonPath("$[1].purchaseId", is(2)))
                .andExpect(jsonPath("$[1].pharmacyId", is(2)))
                .andExpect(jsonPath("$[1].patientId", is(3)))
                .andExpect(jsonPath("$[1].totalAmount", is(200.50)));
    }

    @Test
    void updatePurchase_ShouldReturnUpdatedPurchase() throws Exception {
        // Arrange
        Long purchaseId = 1L;
        PurchaseRequestDTO request = PurchaseRequestDTO.builder()
                .pharmacyId(1L)
                .patientId(2L)
                .prescriptionId(3L)
                .purchaseDate(LocalDate.of(2026, 1, 10))
                .totalAmount(BigDecimal.valueOf(175.50))
                .build();

        PurchaseDTO updatedPurchase = PurchaseDTO.builder()
                .purchaseId(purchaseId)
                .pharmacyId(1L)
                .patientId(2L)
                .prescriptionId(3L)
                .purchaseDate(LocalDate.of(2026, 1, 10))
                .totalAmount(BigDecimal.valueOf(175.50))
                .build();

        when(purchaseService.update(eq(purchaseId), any(PurchaseRequestDTO.class))).thenReturn(updatedPurchase);

        // Act & Assert
        mockMvc.perform(put("/api/purchases/{id}", purchaseId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.purchaseId", is(1)))
                .andExpect(jsonPath("$.pharmacyId", is(1)))
                .andExpect(jsonPath("$.patientId", is(2)))
                .andExpect(jsonPath("$.totalAmount", is(175.50)));
    }

    @Test
    void deletePurchase_ShouldReturnNoContent() throws Exception {
        // Arrange
        Long purchaseId = 1L;
        doNothing().when(purchaseService).delete(purchaseId);

        // Act & Assert
        mockMvc.perform(delete("/api/purchases/{id}", purchaseId))
                .andExpect(status().isNoContent());
    }

    @Test
    void getPurchasesByPatient_ShouldReturnListOfPurchases() throws Exception {
        // Arrange
        Long patientId = 2L;
        PurchaseDTO purchase1 = PurchaseDTO.builder()
                .purchaseId(1L)
                .pharmacyId(1L)
                .patientId(patientId)
                .prescriptionId(3L)
                .purchaseDate(LocalDate.of(2026, 1, 10))
                .totalAmount(BigDecimal.valueOf(150.75))
                .build();

        PurchaseDTO purchase2 = PurchaseDTO.builder()
                .purchaseId(2L)
                .pharmacyId(2L)
                .patientId(patientId)
                .prescriptionId(4L)
                .purchaseDate(LocalDate.of(2026, 1, 11))
                .totalAmount(BigDecimal.valueOf(200.50))
                .build();

        List<PurchaseDTO> purchases = Arrays.asList(purchase1, purchase2);
        when(purchaseService.getByPatient(patientId)).thenReturn(purchases);

        // Act & Assert
        mockMvc.perform(get("/api/purchases/by-patient/{patientId}", patientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].purchaseId", is(1)))
                .andExpect(jsonPath("$[0].patientId", is(2)))
                .andExpect(jsonPath("$[1].purchaseId", is(2)))
                .andExpect(jsonPath("$[1].patientId", is(2)));
    }

    @Test
    void getPurchasesByPharmacy_ShouldReturnListOfPurchases() throws Exception {
        // Arrange
        Long pharmacyId = 1L;
        PurchaseDTO purchase1 = PurchaseDTO.builder()
                .purchaseId(1L)
                .pharmacyId(pharmacyId)
                .patientId(2L)
                .prescriptionId(3L)
                .purchaseDate(LocalDate.of(2026, 1, 10))
                .totalAmount(BigDecimal.valueOf(150.75))
                .build();

        PurchaseDTO purchase2 = PurchaseDTO.builder()
                .purchaseId(3L)
                .pharmacyId(pharmacyId)
                .patientId(4L)
                .prescriptionId(5L)
                .purchaseDate(LocalDate.of(2026, 1, 11))
                .totalAmount(BigDecimal.valueOf(125.25))
                .build();

        List<PurchaseDTO> purchases = Arrays.asList(purchase1, purchase2);
        when(purchaseService.getByPharmacy(pharmacyId)).thenReturn(purchases);

        // Act & Assert
        mockMvc.perform(get("/api/purchases/by-pharmacy/{pharmacyId}", pharmacyId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].purchaseId", is(1)))
                .andExpect(jsonPath("$[0].pharmacyId", is(1)))
                .andExpect(jsonPath("$[1].purchaseId", is(3)))
                .andExpect(jsonPath("$[1].pharmacyId", is(1)));
    }
}
