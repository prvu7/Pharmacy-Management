package com.mpp.pharmacy.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mpp.pharmacy.DTO.PersonDTO;
import com.mpp.pharmacy.Enum.Role;
import com.mpp.pharmacy.Enum.Sex;
import com.mpp.pharmacy.RequestDTO.PersonRequestDTO;
import com.mpp.pharmacy.ServiceInterface.PersonService;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PersonController.class)
@AutoConfigureMockMvc(addFilters = false)
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PersonService personService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void create_ShouldReturnCreatedPerson() throws Exception {
        // Arrange
        PersonRequestDTO request = new PersonRequestDTO();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setSex(Sex.M);
        request.setDateOfBirth(LocalDate.of(1990, 5, 15));
        request.setPhone("+1234567890");
        request.setEmail("john.doe@example.com");
        request.setAddress("123 Main Street");
        request.setRole(Role.patient);

        PersonDTO response = PersonDTO.builder()
                .personId(1L)
                .firstName("John")
                .lastName("Doe")
                .sex("M")
                .dateOfBirth("1990-05-15")
                .phone("+1234567890")
                .email("john.doe@example.com")
                .address("123 Main Street")
                .role("PATIENT")
                .build();

        when(personService.create(any(PersonRequestDTO.class))).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/api/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.personId").value(1))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.sex").value("M"))
                .andExpect(jsonPath("$.dateOfBirth").value("1990-05-15"))
                .andExpect(jsonPath("$.phone").value("+1234567890"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.address").value("123 Main Street"))
                .andExpect(jsonPath("$.role").value("PATIENT"));
    }

    @Test
    void getById_ShouldReturnPerson() throws Exception {
        // Arrange
        Long personId = 1L;

        PersonDTO response = PersonDTO.builder()
                .personId(personId)
                .firstName("Jane")
                .lastName("Smith")
                .sex("F")
                .dateOfBirth("1985-08-20")
                .phone("+9876543210")
                .email("jane.smith@example.com")
                .address("456 Oak Avenue")
                .role("DOCTOR")
                .build();

        when(personService.getById(eq(personId))).thenReturn(response);

        // Act & Assert
        mockMvc.perform(get("/api/persons/{id}", personId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.personId").value(1))
                .andExpect(jsonPath("$.firstName").value("Jane"))
                .andExpect(jsonPath("$.lastName").value("Smith"))
                .andExpect(jsonPath("$.sex").value("F"))
                .andExpect(jsonPath("$.email").value("jane.smith@example.com"))
                .andExpect(jsonPath("$.role").value("DOCTOR"));
    }

    @Test
    void getAll_ShouldReturnListOfPersons() throws Exception {
        // Arrange
        PersonDTO person1 = PersonDTO.builder()
                .personId(1L)
                .firstName("John")
                .lastName("Doe")
                .sex("M")
                .dateOfBirth("1990-05-15")
                .phone("+1234567890")
                .email("john.doe@example.com")
                .address("123 Main Street")
                .role("PATIENT")
                .build();

        PersonDTO person2 = PersonDTO.builder()
                .personId(2L)
                .firstName("Jane")
                .lastName("Smith")
                .sex("F")
                .dateOfBirth("1985-08-20")
                .phone("+9876543210")
                .email("jane.smith@example.com")
                .address("456 Oak Avenue")
                .role("DOCTOR")
                .build();

        PersonDTO person3 = PersonDTO.builder()
                .personId(3L)
                .firstName("Bob")
                .lastName("Johnson")
                .sex("M")
                .dateOfBirth("1978-12-10")
                .phone("+1122334455")
                .email("bob.johnson@example.com")
                .address("789 Pine Road")
                .role("PHARMACIST")
                .build();

        when(personService.getAll()).thenReturn(List.of(person1, person2, person3));

        // Act & Assert
        mockMvc.perform(get("/api/persons"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(3))
                .andExpect(jsonPath("$[0].personId").value(1))
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].role").value("PATIENT"))
                .andExpect(jsonPath("$[1].personId").value(2))
                .andExpect(jsonPath("$[1].firstName").value("Jane"))
                .andExpect(jsonPath("$[1].role").value("DOCTOR"))
                .andExpect(jsonPath("$[2].personId").value(3))
                .andExpect(jsonPath("$[2].firstName").value("Bob"))
                .andExpect(jsonPath("$[2].role").value("PHARMACIST"));
    }

    @Test
    void update_ShouldReturnUpdatedPerson() throws Exception {
        // Arrange
        Long personId = 1L;

        PersonRequestDTO request = new PersonRequestDTO();
        request.setFirstName("John");
        request.setLastName("Updated");
        request.setSex(Sex.M);
        request.setDateOfBirth(LocalDate.of(1990, 5, 15));
        request.setPhone("+1234567890");
        request.setEmail("john.updated@example.com");
        request.setAddress("999 Updated Street");
        request.setRole(Role.patient);

        PersonDTO response = PersonDTO.builder()
                .personId(personId)
                .firstName("John")
                .lastName("Updated")
                .sex("M")
                .dateOfBirth("1990-05-15")
                .phone("+1234567890")
                .email("john.updated@example.com")
                .address("999 Updated Street")
                .role("PATIENT")
                .build();

        when(personService.update(eq(personId), any(PersonRequestDTO.class))).thenReturn(response);

        // Act & Assert
        mockMvc.perform(put("/api/persons/{id}", personId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.personId").value(1))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Updated"))
                .andExpect(jsonPath("$.email").value("john.updated@example.com"))
                .andExpect(jsonPath("$.address").value("999 Updated Street"));
    }

    @Test
    void delete_ShouldReturnNoContent() throws Exception {
        // Arrange
        Long personId = 1L;
        doNothing().when(personService).delete(eq(personId));

        // Act & Assert
        mockMvc.perform(delete("/api/persons/{id}", personId))
                .andExpect(status().isNoContent());
    }
}
