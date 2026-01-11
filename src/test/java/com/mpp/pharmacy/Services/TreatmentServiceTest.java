package com.mpp.pharmacy.Services;

import com.mpp.pharmacy.DTO.TreatmentDTO;
import com.mpp.pharmacy.Domain.TreatmentDomain;
import com.mpp.pharmacy.Entity.Person;
import com.mpp.pharmacy.Entity.Treatment;
import com.mpp.pharmacy.Exception.InvalidRequestException;
import com.mpp.pharmacy.Mapper.TreatmentMapper;
import com.mpp.pharmacy.RequestDTO.TreatmentRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TreatmentServiceTest {

    @Mock
    private TreatmentDomain treatmentDomain;

    @Mock
    private TreatmentMapper treatmentMapper;

    @InjectMocks
    private TreatmentServiceImpl treatmentService;

    private TreatmentRequestDTO request;
    private Treatment treatmentEntity;
    private TreatmentDTO treatmentDTO;
    private Person doctor;

    @BeforeEach
    void setUp() {
        request = TreatmentRequestDTO.builder()
                .treatmentName("Hypertension Treatment")
                .description("Treatment plan for high blood pressure")
                .doctorId(200L)
                .startDate(LocalDate.of(2025, 1, 1))
                .endDate(LocalDate.of(2025, 6, 30))
                .build();

        doctor = Person.builder()
                .personId(200L)
                .firstName("Dr. Jane")
                .lastName("Smith")
                .build();

        treatmentEntity = Treatment.builder()
                .treatmentId(1L)
                .treatmentName("Hypertension Treatment")
                .description("Treatment plan for high blood pressure")
                .doctor(doctor)
                .startDate(LocalDate.of(2025, 1, 1))
                .endDate(LocalDate.of(2025, 6, 30))
                .build();

        treatmentDTO = TreatmentDTO.builder()
                .treatmentId(1L)
                .treatmentName("Hypertension Treatment")
                .description("Treatment plan for high blood pressure")
                .doctorId(200L)
                .startDate(LocalDate.of(2025, 1, 1))
                .endDate(LocalDate.of(2025, 6, 30))
                .build();
    }

    @Test
    void createTreatment_ShouldReturnTreatmentDTO() {
        when(treatmentDomain.create(request)).thenReturn(treatmentEntity);
        when(treatmentMapper.toDTO(treatmentEntity)).thenReturn(treatmentDTO);

        TreatmentDTO result = treatmentService.create(request);

        assertNotNull(result);
        assertEquals(treatmentDTO.getTreatmentId(), result.getTreatmentId());
        assertEquals(treatmentDTO.getTreatmentName(), result.getTreatmentName());
        assertEquals(treatmentDTO.getDescription(), result.getDescription());
        assertEquals(treatmentDTO.getDoctorId(), result.getDoctorId());
        assertEquals(treatmentDTO.getStartDate(), result.getStartDate());
        assertEquals(treatmentDTO.getEndDate(), result.getEndDate());
        verify(treatmentDomain, times(1)).create(request);
        verify(treatmentMapper, times(1)).toDTO(treatmentEntity);
    }

    @Test
    void createTreatment_WithNullRequest_ShouldThrowException() {
        InvalidRequestException exception = assertThrows(
                InvalidRequestException.class,
                () -> treatmentService.create(null)
        );

        assertEquals("Treatment request cannot be null", exception.getMessage());
        verify(treatmentDomain, never()).create(any());
    }

    @Test
    void getById_ShouldReturnTreatmentDTO() {
        Long treatmentId = 1L;
        when(treatmentDomain.getById(treatmentId)).thenReturn(treatmentEntity);
        when(treatmentMapper.toDTO(treatmentEntity)).thenReturn(treatmentDTO);

        TreatmentDTO result = treatmentService.getById(treatmentId);

        assertNotNull(result);
        assertEquals(treatmentDTO.getTreatmentId(), result.getTreatmentId());
        assertEquals(treatmentDTO.getTreatmentName(), result.getTreatmentName());
        assertEquals(treatmentDTO.getDescription(), result.getDescription());
        assertEquals(treatmentDTO.getDoctorId(), result.getDoctorId());
        assertEquals(treatmentDTO.getStartDate(), result.getStartDate());
        assertEquals(treatmentDTO.getEndDate(), result.getEndDate());
        verify(treatmentDomain, times(1)).getById(treatmentId);
        verify(treatmentMapper, times(1)).toDTO(treatmentEntity);
    }

    @Test
    void getById_WithNullId_ShouldThrowException() {
        InvalidRequestException exception = assertThrows(
                InvalidRequestException.class,
                () -> treatmentService.getById(null)
        );

        assertEquals("Treatment ID cannot be null", exception.getMessage());
        verify(treatmentDomain, never()).getById(any());
    }

    @Test
    void getAll_ShouldReturnListOfTreatmentDTOs() {
        Person doctor2 = Person.builder()
                .personId(201L)
                .firstName("Dr. Bob")
                .lastName("Williams")
                .build();

        Treatment treatment2 = Treatment.builder()
                .treatmentId(2L)
                .treatmentName("Diabetes Treatment")
                .description("Treatment plan for diabetes management")
                .doctor(doctor2)
                .startDate(LocalDate.of(2025, 2, 1))
                .endDate(LocalDate.of(2025, 12, 31))
                .build();

        TreatmentDTO treatmentDTO2 = TreatmentDTO.builder()
                .treatmentId(2L)
                .treatmentName("Diabetes Treatment")
                .description("Treatment plan for diabetes management")
                .doctorId(201L)
                .startDate(LocalDate.of(2025, 2, 1))
                .endDate(LocalDate.of(2025, 12, 31))
                .build();

        Treatment treatment3 = Treatment.builder()
                .treatmentId(3L)
                .treatmentName("Physical Therapy")
                .description("Post-surgery rehabilitation")
                .doctor(doctor)
                .startDate(LocalDate.of(2025, 3, 1))
                .endDate(LocalDate.of(2025, 5, 31))
                .build();

        TreatmentDTO treatmentDTO3 = TreatmentDTO.builder()
                .treatmentId(3L)
                .treatmentName("Physical Therapy")
                .description("Post-surgery rehabilitation")
                .doctorId(200L)
                .startDate(LocalDate.of(2025, 3, 1))
                .endDate(LocalDate.of(2025, 5, 31))
                .build();

        List<Treatment> treatments = List.of(treatmentEntity, treatment2, treatment3);
        when(treatmentDomain.getAll()).thenReturn(treatments);
        when(treatmentMapper.toDTO(treatmentEntity)).thenReturn(treatmentDTO);
        when(treatmentMapper.toDTO(treatment2)).thenReturn(treatmentDTO2);
        when(treatmentMapper.toDTO(treatment3)).thenReturn(treatmentDTO3);

        List<TreatmentDTO> result = treatmentService.getAll();

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(treatmentDTO.getTreatmentId(), result.get(0).getTreatmentId());
        assertEquals(treatmentDTO.getTreatmentName(), result.get(0).getTreatmentName());
        assertEquals(treatmentDTO2.getTreatmentId(), result.get(1).getTreatmentId());
        assertEquals(treatmentDTO2.getTreatmentName(), result.get(1).getTreatmentName());
        assertEquals(treatmentDTO3.getTreatmentId(), result.get(2).getTreatmentId());
        assertEquals(treatmentDTO3.getTreatmentName(), result.get(2).getTreatmentName());
        verify(treatmentDomain, times(1)).getAll();
        verify(treatmentMapper, times(3)).toDTO(any(Treatment.class));
    }

    @Test
    void update_ShouldReturnUpdatedTreatmentDTO() {
        Long treatmentId = 1L;

        TreatmentRequestDTO updateRequest = TreatmentRequestDTO.builder()
                .treatmentName("Hypertension Treatment - Updated")
                .description("Updated treatment plan for high blood pressure with new medications")
                .doctorId(200L)
                .startDate(LocalDate.of(2025, 1, 1))
                .endDate(LocalDate.of(2025, 9, 30))
                .build();

        Treatment updatedEntity = Treatment.builder()
                .treatmentId(treatmentId)
                .treatmentName("Hypertension Treatment - Updated")
                .description("Updated treatment plan for high blood pressure with new medications")
                .doctor(doctor)
                .startDate(LocalDate.of(2025, 1, 1))
                .endDate(LocalDate.of(2025, 9, 30))
                .build();

        TreatmentDTO updatedDTO = TreatmentDTO.builder()
                .treatmentId(treatmentId)
                .treatmentName("Hypertension Treatment - Updated")
                .description("Updated treatment plan for high blood pressure with new medications")
                .doctorId(200L)
                .startDate(LocalDate.of(2025, 1, 1))
                .endDate(LocalDate.of(2025, 9, 30))
                .build();

        when(treatmentDomain.update(treatmentId, updateRequest)).thenReturn(updatedEntity);
        when(treatmentMapper.toDTO(updatedEntity)).thenReturn(updatedDTO);

        TreatmentDTO result = treatmentService.update(treatmentId, updateRequest);

        assertNotNull(result);
        assertEquals(treatmentId, result.getTreatmentId());
        assertEquals("Hypertension Treatment - Updated", result.getTreatmentName());
        assertEquals("Updated treatment plan for high blood pressure with new medications", result.getDescription());
        assertEquals(LocalDate.of(2025, 9, 30), result.getEndDate());
        verify(treatmentDomain, times(1)).update(treatmentId, updateRequest);
        verify(treatmentMapper, times(1)).toDTO(updatedEntity);
    }

    @Test
    void update_WithNullId_ShouldThrowException() {
        InvalidRequestException exception = assertThrows(
                InvalidRequestException.class,
                () -> treatmentService.update(null, request)
        );

        assertEquals("Treatment ID cannot be null", exception.getMessage());
        verify(treatmentDomain, never()).update(any(), any());
    }

    @Test
    void update_WithNullRequest_ShouldThrowException() {
        Long treatmentId = 1L;
        InvalidRequestException exception = assertThrows(
                InvalidRequestException.class,
                () -> treatmentService.update(treatmentId, null)
        );

        assertEquals("Treatment request cannot be null", exception.getMessage());
        verify(treatmentDomain, never()).update(any(), any());
    }

    @Test
    void delete_ShouldCallDomainDelete() {
        Long treatmentId = 1L;
        doNothing().when(treatmentDomain).delete(treatmentId);

        treatmentService.delete(treatmentId);

        verify(treatmentDomain, times(1)).delete(treatmentId);
    }

    @Test
    void delete_WithNullId_ShouldThrowException() {
        InvalidRequestException exception = assertThrows(
                InvalidRequestException.class,
                () -> treatmentService.delete(null)
        );

        assertEquals("Treatment ID cannot be null", exception.getMessage());
        verify(treatmentDomain, never()).delete(any());
    }
}