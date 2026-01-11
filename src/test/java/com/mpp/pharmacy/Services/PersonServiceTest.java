package com.mpp.pharmacy.Services;

import com.mpp.pharmacy.DTO.PersonDTO;
import com.mpp.pharmacy.Entity.Person;
import com.mpp.pharmacy.Enum.Role;
import com.mpp.pharmacy.Enum.Sex;
import com.mpp.pharmacy.Mapper.PersonMapper;
import com.mpp.pharmacy.Domain.PersonDomain;
import com.mpp.pharmacy.RequestDTO.PersonRequestDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Mock
    private PersonDomain domain;

    @Mock
    private PersonMapper mapper;

    @InjectMocks
    private PersonServiceImpl personService;

    @Test
    void create_ShouldReturnCreatedPerson() {
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

        Person savedPerson = Person.builder()
                .personId(1L)
                .firstName("John")
                .lastName("Doe")
                .sex(Sex.M)
                .dateOfBirth(LocalDate.of(1990, 5, 15))
                .phone("+1234567890")
                .email("john.doe@example.com")
                .address("123 Main Street")
                .role(Role.patient)
                .build();

        PersonDTO expectedDTO = PersonDTO.builder()
                .personId(1L)
                .firstName("John")
                .lastName("Doe")
                .sex("M")
                .dateOfBirth("1990-05-15")
                .phone("+1234567890")
                .email("john.doe@example.com")
                .address("123 Main Street")
                .role("patient")
                .build();

        when(domain.create(any(PersonRequestDTO.class))).thenReturn(savedPerson);  // Changed
        when(mapper.toDTO(any(Person.class))).thenReturn(expectedDTO);

        // Act
        PersonDTO result = personService.create(request);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getPersonId());
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals("M", result.getSex());
        assertEquals("1990-05-15", result.getDateOfBirth());
        assertEquals("+1234567890", result.getPhone());
        assertEquals("john.doe@example.com", result.getEmail());
        assertEquals("123 Main Street", result.getAddress());
        assertEquals("patient", result.getRole());
    }

    @Test
    void getById_ShouldReturnPerson() {
        Long personId = 1L;
        
        Person person = Person.builder()
                .personId(personId)
                .firstName("John")
                .lastName("Doe")
                .sex(Sex.M)
                .dateOfBirth(LocalDate.of(1990, 5, 15))
                .phone("+1234567890")
                .email("john.doe@example.com")
                .address("123 Main Street")
                .role(Role.patient)
                .build();

        PersonDTO expectedDTO = PersonDTO.builder()
                .personId(personId)
                .firstName("John")
                .lastName("Doe")
                .sex("M")
                .dateOfBirth("1990-05-15")
                .phone("+1234567890")
                .email("john.doe@example.com")
                .address("123 Main Street")
                .role("patient")
                .build();

        when(domain.getById(personId)).thenReturn(person);
        when(mapper.toDTO(person)).thenReturn(expectedDTO);

        // Act
        PersonDTO result = personService.getById(personId);

        // Assert
        assertNotNull(result);
        assertEquals(personId, result.getPersonId());
    }
}