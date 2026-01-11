package com.mpp.pharmacy.Services;

import com.mpp.pharmacy.DTO.DrugDTO;
import com.mpp.pharmacy.Entity.Drug;
import com.mpp.pharmacy.RequestDTO.DrugRequestDTO;
import com.mpp.pharmacy.Domain.DrugDomain;
import com.mpp.pharmacy.Mapper.DrugMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class DrugServiceTest {

    @Mock
    private DrugDomain drugDomain;

    @Mock
    private DrugMapper drugMapper;

    @InjectMocks
    private DrugServiceImpl drugService;

    private DrugRequestDTO request;
    private Drug drugEntity;
    private DrugDTO drugDTO;

    @BeforeEach
    void setUp() {
        request = DrugRequestDTO.builder()
                .drugName("Aspirin")
                .genericName("Acetylsalicylic acid")
                .description("Pain reliever")
                .dosageForm("Tablet")
                .manufacturer("Pharma Inc")
                .price(BigDecimal.valueOf(10.50))
                .build();

        drugEntity = Drug.builder()
                .drugId(1L)
                .drugName("Aspirin")
                .genericName("Acetylsalicylic acid")
                .description("Pain reliever")
                .dosageForm("Tablet")
                .manufacturer("Pharma Inc")
                .price(BigDecimal.valueOf(10.50))
                .build();

        drugDTO = DrugDTO.builder()
                .drugId(1L)
                .drugName("Aspirin")
                .genericName("Acetylsalicylic acid")
                .description("Pain reliever")
                .dosageForm("Tablet")
                .manufacturer("Pharma Inc")
                .price(BigDecimal.valueOf(10.50))
                .build();
    }

    @Test
    void createDrug_ShouldReturnDrugDTO() {
        when(drugDomain.create(request)).thenReturn(drugEntity);
        when(drugMapper.toDTO(drugEntity)).thenReturn(drugDTO);

        DrugDTO result = drugService.create(request);

        assertNotNull(result);
        assertEquals(drugDTO.getDrugId(), result.getDrugId());
        assertEquals(drugDTO.getDrugName(), result.getDrugName());
        verify(drugDomain, times(1)).create(request);
        verify(drugMapper, times(1)).toDTO(drugEntity);
    }

    @Test
    void createDrug_WithNullRequest_ShouldThrowException() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> drugService.create(null)
        );
        
        assertEquals("Request cannot be null", exception.getMessage());
        verify(drugDomain, never()).create(any());
    }

    @Test
    void getById_ShouldReturnDrugDTO() {
        Long drugId = 1L;
        when(drugDomain.getById(drugId)).thenReturn(drugEntity);
        when(drugMapper.toDTO(drugEntity)).thenReturn(drugDTO);

        DrugDTO result = drugService.getById(drugId);

        assertNotNull(result);
        assertEquals(drugDTO.getDrugId(), result.getDrugId());
        verify(drugDomain, times(1)).getById(drugId);
        verify(drugMapper, times(1)).toDTO(drugEntity);
    }

    @Test
    void getById_WithNullId_ShouldThrowException() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> drugService.getById(null)
        );
        
        assertEquals("ID cannot be null", exception.getMessage());
        verify(drugDomain, never()).getById(any());
    }

    @Test
    void getAll_ShouldReturnListOfDrugDTOs() {
        List<Drug> drugs = List.of(drugEntity);
        when(drugDomain.getAll()).thenReturn(drugs);
        when(drugMapper.toDTO(drugEntity)).thenReturn(drugDTO);

        List<DrugDTO> result = drugService.getAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(drugDomain, times(1)).getAll();
        verify(drugMapper, times(1)).toDTO(drugEntity);
    }

    @Test
    void update_ShouldReturnUpdatedDrugDTO() {
        Long drugId = 1L;
        when(drugDomain.update(drugId, request)).thenReturn(drugEntity);
        when(drugMapper.toDTO(drugEntity)).thenReturn(drugDTO);

        DrugDTO result = drugService.update(drugId, request);

        assertNotNull(result);
        assertEquals(drugDTO.getDrugId(), result.getDrugId());
        verify(drugDomain, times(1)).update(drugId, request);
        verify(drugMapper, times(1)).toDTO(drugEntity);
    }

    @Test
    void update_WithNullId_ShouldThrowException() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> drugService.update(null, request)
        );
        
        assertEquals("ID cannot be null", exception.getMessage());
        verify(drugDomain, never()).update(any(), any());
    }

    @Test
    void update_WithNullRequest_ShouldThrowException() {
        Long drugId = 1L;
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> drugService.update(drugId, null)
        );
        
        assertEquals("Request cannot be null", exception.getMessage());
        verify(drugDomain, never()).update(any(), any());
    }

    @Test
    void delete_ShouldCallDomainDelete() {
        Long drugId = 1L;
        doNothing().when(drugDomain).delete(drugId);

        drugService.delete(drugId);

        verify(drugDomain, times(1)).delete(drugId);
    }

    @Test
    void delete_WithNullId_ShouldThrowException() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> drugService.delete(null)
        );
        
        assertEquals("ID cannot be null", exception.getMessage());
        verify(drugDomain, never()).delete(any());
    }
}