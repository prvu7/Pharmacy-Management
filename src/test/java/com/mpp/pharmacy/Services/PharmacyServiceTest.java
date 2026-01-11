package com.mpp.pharmacy.Services;

import com.mpp.pharmacy.DTO.PharmacyDTO;
import com.mpp.pharmacy.Entity.Pharmacy;
import com.mpp.pharmacy.RequestDTO.PharmacyRequestDTO;
import com.mpp.pharmacy.Domain.PharmacyDomain;
import com.mpp.pharmacy.Mapper.PharmacyMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PharmacyServiceTest {

    @Mock
    private PharmacyDomain pharmacyDomain;

    @Mock
    private PharmacyMapper pharmacyMapper;

    @InjectMocks
    private PharmacyServiceImpl pharmacyService;

    private PharmacyRequestDTO request;
    private Pharmacy pharmacyEntity;
    private PharmacyDTO pharmacyDTO;

    @BeforeEach
    void setUp() {
        request = PharmacyRequestDTO.builder()
                .name("HealthPlus Pharmacy")
                .address("123 Main Street, Downtown, City")
                .phone("+1234567890")
                .build();

        pharmacyEntity = Pharmacy.builder()
                .pharmacyId(1L)
                .name("HealthPlus Pharmacy")
                .address("123 Main Street, Downtown, City")
                .phone("+1234567890")
                .build();

        pharmacyDTO = PharmacyDTO.builder()
                .pharmacyId(1L)
                .name("HealthPlus Pharmacy")
                .address("123 Main Street, Downtown, City")
                .phone("+1234567890")
                .build();
    }

    @Test
    void createPharmacy_ShouldReturnPharmacyDTO() {
        when(pharmacyDomain.create(request)).thenReturn(pharmacyEntity);
        when(pharmacyMapper.toDTO(pharmacyEntity)).thenReturn(pharmacyDTO);

        PharmacyDTO result = pharmacyService.create(request);

        assertNotNull(result);
        assertEquals(pharmacyDTO.getPharmacyId(), result.getPharmacyId());
        assertEquals(pharmacyDTO.getName(), result.getName());
        assertEquals(pharmacyDTO.getAddress(), result.getAddress());
        assertEquals(pharmacyDTO.getPhone(), result.getPhone());
        verify(pharmacyDomain, times(1)).create(request);
        verify(pharmacyMapper, times(1)).toDTO(pharmacyEntity);
    }

    @Test
    void createPharmacy_WithNullRequest_ShouldThrowException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> pharmacyService.create(null)
        );

        assertEquals("Pharmacy request cannot be null", exception.getMessage());
        verify(pharmacyDomain, never()).create(any());
    }

    @Test
    void getById_ShouldReturnPharmacyDTO() {
        Long pharmacyId = 1L;
        when(pharmacyDomain.getById(pharmacyId)).thenReturn(pharmacyEntity);
        when(pharmacyMapper.toDTO(pharmacyEntity)).thenReturn(pharmacyDTO);

        PharmacyDTO result = pharmacyService.getById(pharmacyId);

        assertNotNull(result);
        assertEquals(pharmacyDTO.getPharmacyId(), result.getPharmacyId());
        assertEquals(pharmacyDTO.getName(), result.getName());
        assertEquals(pharmacyDTO.getAddress(), result.getAddress());
        assertEquals(pharmacyDTO.getPhone(), result.getPhone());
        verify(pharmacyDomain, times(1)).getById(pharmacyId);
        verify(pharmacyMapper, times(1)).toDTO(pharmacyEntity);
    }

    @Test
    void getById_WithNullId_ShouldThrowException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> pharmacyService.getById(null)
        );

        assertEquals("Pharmacy ID cannot be null", exception.getMessage());
        verify(pharmacyDomain, never()).getById(any());
    }

    @Test
    void getAll_ShouldReturnListOfPharmacyDTOs() {
        Pharmacy pharmacy2 = Pharmacy.builder()
                .pharmacyId(2L)
                .name("CareWell Pharmacy")
                .address("456 Oak Avenue, Suburb, City")
                .phone("+9876543210")
                .build();

        PharmacyDTO pharmacyDTO2 = PharmacyDTO.builder()
                .pharmacyId(2L)
                .name("CareWell Pharmacy")
                .address("456 Oak Avenue, Suburb, City")
                .phone("+9876543210")
                .build();

        Pharmacy pharmacy3 = Pharmacy.builder()
                .pharmacyId(3L)
                .name("MediCare & Co.")
                .address("789 Pine Road, Uptown, City")
                .phone("+1122334455")
                .build();

        PharmacyDTO pharmacyDTO3 = PharmacyDTO.builder()
                .pharmacyId(3L)
                .name("MediCare & Co.")
                .address("789 Pine Road, Uptown, City")
                .phone("+1122334455")
                .build();

        List<Pharmacy> pharmacies = List.of(pharmacyEntity, pharmacy2, pharmacy3);
        when(pharmacyDomain.getAll()).thenReturn(pharmacies);
        when(pharmacyMapper.toDTO(pharmacyEntity)).thenReturn(pharmacyDTO);
        when(pharmacyMapper.toDTO(pharmacy2)).thenReturn(pharmacyDTO2);
        when(pharmacyMapper.toDTO(pharmacy3)).thenReturn(pharmacyDTO3);

        List<PharmacyDTO> result = pharmacyService.getAll();

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(pharmacyDTO.getPharmacyId(), result.get(0).getPharmacyId());
        assertEquals(pharmacyDTO.getName(), result.get(0).getName());
        assertEquals(pharmacyDTO2.getPharmacyId(), result.get(1).getPharmacyId());
        assertEquals(pharmacyDTO2.getName(), result.get(1).getName());
        assertEquals(pharmacyDTO3.getPharmacyId(), result.get(2).getPharmacyId());
        assertEquals(pharmacyDTO3.getName(), result.get(2).getName());
        verify(pharmacyDomain, times(1)).getAll();
        verify(pharmacyMapper, times(3)).toDTO(any(Pharmacy.class));
    }

    @Test
    void update_ShouldReturnUpdatedPharmacyDTO() {
        Long pharmacyId = 1L;

        PharmacyRequestDTO updateRequest = PharmacyRequestDTO.builder()
                .name("HealthPlus Pharmacy - Updated")
                .address("123 Main Street, Suite 200, Downtown, City")
                .phone("+1234567899")
                .build();

        Pharmacy updatedEntity = Pharmacy.builder()
                .pharmacyId(pharmacyId)
                .name("HealthPlus Pharmacy - Updated")
                .address("123 Main Street, Suite 200, Downtown, City")
                .phone("+1234567899")
                .build();

        PharmacyDTO updatedDTO = PharmacyDTO.builder()
                .pharmacyId(pharmacyId)
                .name("HealthPlus Pharmacy - Updated")
                .address("123 Main Street, Suite 200, Downtown, City")
                .phone("+1234567899")
                .build();

        when(pharmacyDomain.update(pharmacyId, updateRequest)).thenReturn(updatedEntity);
        when(pharmacyMapper.toDTO(updatedEntity)).thenReturn(updatedDTO);

        PharmacyDTO result = pharmacyService.update(pharmacyId, updateRequest);

        assertNotNull(result);
        assertEquals(pharmacyId, result.getPharmacyId());
        assertEquals("HealthPlus Pharmacy - Updated", result.getName());
        assertEquals("123 Main Street, Suite 200, Downtown, City", result.getAddress());
        assertEquals("+1234567899", result.getPhone());
        verify(pharmacyDomain, times(1)).update(pharmacyId, updateRequest);
        verify(pharmacyMapper, times(1)).toDTO(updatedEntity);
    }

    @Test
    void update_WithNullId_ShouldThrowException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> pharmacyService.update(null, request)
        );

        assertEquals("Pharmacy ID cannot be null", exception.getMessage());
        verify(pharmacyDomain, never()).update(any(), any());
    }

    @Test
    void update_WithNullRequest_ShouldThrowException() {
        Long pharmacyId = 1L;
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> pharmacyService.update(pharmacyId, null)
        );

        assertEquals("Pharmacy request cannot be null", exception.getMessage());
        verify(pharmacyDomain, never()).update(any(), any());
    }

    @Test
    void delete_ShouldCallDomainDelete() {
        Long pharmacyId = 1L;
        doNothing().when(pharmacyDomain).delete(pharmacyId);

        pharmacyService.delete(pharmacyId);

        verify(pharmacyDomain, times(1)).delete(pharmacyId);
    }

    @Test
    void delete_WithNullId_ShouldThrowException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> pharmacyService.delete(null)
        );

        assertEquals("Pharmacy ID cannot be null", exception.getMessage());
        verify(pharmacyDomain, never()).delete(any());
    }
}