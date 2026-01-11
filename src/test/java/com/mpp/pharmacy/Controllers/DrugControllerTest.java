package com.mpp.pharmacy.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mpp.pharmacy.DTO.DrugDTO;
import com.mpp.pharmacy.RequestDTO.DrugRequestDTO;
import com.mpp.pharmacy.ServiceInterface.DrugService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
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

@WebMvcTest(DrugController.class)
@AutoConfigureMockMvc(addFilters = false)
public class DrugControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DrugService drugService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void create_ShouldReturnCreatedDrug() throws Exception {
        DrugRequestDTO request = DrugRequestDTO.builder()
                .drugName("Aspirin")
                .genericName("Acetylsalicylic Acid")
                .description("Pain reliever")
                .dosageForm("Tablet")
                .manufacturer("Pharma Inc.")
                .price(BigDecimal.valueOf(10.50))
                .build();

        DrugDTO createdDrug = DrugDTO.builder()
                .drugId(1L)
                .drugName("Aspirin")
                .genericName("Acetylsalicylic Acid")
                .description("Pain reliever")
                .dosageForm("Tablet")
                .manufacturer("Pharma Inc.")
                .price(BigDecimal.valueOf(10.50))
                .build();

        when(drugService.create(any(DrugRequestDTO.class))).thenReturn(createdDrug);

        mockMvc.perform(post("/api/drugs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.drugId", is(1)))
                .andExpect(jsonPath("$.drugName", is("Aspirin")))
                .andExpect(jsonPath("$.genericName", is("Acetylsalicylic Acid")))
                .andExpect(jsonPath("$.description", is("Pain reliever")))
                .andExpect(jsonPath("$.dosageForm", is("Tablet")))
                .andExpect(jsonPath("$.manufacturer", is("Pharma Inc.")))
                .andExpect(jsonPath("$.price", is(10.50)));
    }

    @Test
    void getAllDrugs_ShouldReturnListOfDrugs() throws Exception {
        DrugDTO drug1 = DrugDTO.builder()
                .drugId(1L)
                .drugName("Aspirin")
                .genericName("Acetylsalicylic Acid")
                .description("Pain reliever")
                .dosageForm("Tablet")
                .manufacturer("Pharma Inc.")
                .price(BigDecimal.valueOf(10.50))
                .build();

        DrugDTO drug2 = DrugDTO.builder()
                .drugId(2L)
                .drugName("Ibuprofen")
                .genericName("Ibuprofen")
                .description("Anti-inflammatory")
                .dosageForm("Capsule")
                .manufacturer("MedCo")
                .price(BigDecimal.valueOf(15.00))
                .build();

        List<DrugDTO> drugs = Arrays.asList(drug1, drug2);

        when(drugService.getAll()).thenReturn(drugs);

        mockMvc.perform(get("/api/drugs")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].drugId", is(1)))
                .andExpect(jsonPath("$[0].drugName", is("Aspirin")))
                .andExpect(jsonPath("$[1].drugId", is(2)))
                .andExpect(jsonPath("$[1].drugName", is("Ibuprofen")));
    }

    @Test
    void getById_ShouldReturnSpecificDrug() throws Exception {
        DrugDTO drug = DrugDTO.builder()
                .drugId(1L)
                .drugName("Aspirin")
                .genericName("Acetylsalicylic Acid")
                .description("Pain reliever")
                .dosageForm("Tablet")
                .manufacturer("Pharma Inc.")
                .price(BigDecimal.valueOf(10.50))
                .build();

        when(drugService.getById(anyLong())).thenReturn(drug);

        mockMvc.perform(get("/api/drugs/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.drugId", is(1)))
                .andExpect(jsonPath("$.drugName", is("Aspirin")))
                .andExpect(jsonPath("$.genericName", is("Acetylsalicylic Acid")))
                .andExpect(jsonPath("$.description", is("Pain reliever")))
                .andExpect(jsonPath("$.dosageForm", is("Tablet")))
                .andExpect(jsonPath("$.manufacturer", is("Pharma Inc.")))
                .andExpect(jsonPath("$.price", is(10.50)));
    }

    @Test
    void updateDrug_ShouldReturnUpdatedDrug() throws Exception {
        DrugRequestDTO request = DrugRequestDTO.builder()
                .drugName("Aspirin Extra Strength")
                .genericName("Acetylsalicylic Acid")
                .description("Pain reliever - Extra Strength")
                .dosageForm("Tablet")
                .manufacturer("Pharma Inc.")
                .price(BigDecimal.valueOf(12.99))
                .build();

        DrugDTO updatedDrug = DrugDTO.builder()
                .drugId(1L)
                .drugName("Aspirin Extra Strength")
                .genericName("Acetylsalicylic Acid")
                .description("Pain reliever - Extra Strength")
                .dosageForm("Tablet")
                .manufacturer("Pharma Inc.")
                .price(BigDecimal.valueOf(12.99))
                .build();

        when(drugService.update(eq(1L), any(DrugRequestDTO.class))).thenReturn(updatedDrug);

        mockMvc.perform(put("/api/drugs/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.drugId", is(1)))
                .andExpect(jsonPath("$.drugName", is("Aspirin Extra Strength")))
                .andExpect(jsonPath("$.description", is("Pain reliever - Extra Strength")))
                .andExpect(jsonPath("$.price", is(12.99)));
    }

    @Test
    void deleteDrug_ShouldReturnNoContent() throws Exception {
        doNothing().when(drugService).delete(anyLong());

        mockMvc.perform(delete("/api/drugs/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
