package com.mpp.pharmacy.Services;

import com.mpp.pharmacy.DTO.PrescriptionDTO;
import com.mpp.pharmacy.Entity.Person;
import com.mpp.pharmacy.Entity.Prescription;
import com.mpp.pharmacy.Entity.Treatment;
import com.mpp.pharmacy.RequestDTO.PrescriptionRequestDTO;
import com.mpp.pharmacy.Domain.PrescriptionDomain;
import com.mpp.pharmacy.Mapper.PrescriptionMapper;
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
public class PrescriptionServiceTest {

    @Mock
    private PrescriptionDomain prescriptionDomain;

    @Mock
    private PrescriptionMapper prescriptionMapper;

    @InjectMocks
    private PrescriptionServiceImpl prescriptionService;

    private PrescriptionRequestDTO request;
    private Prescription prescriptionEntity;
    private PrescriptionDTO prescriptionDTO;
    private Person patient;
    private Person doctor;
    private Treatment treatment;

    @BeforeEach
    void setUp() {
        request = PrescriptionRequestDTO.builder()
                .patientId(100L)
                .doctorId(200L)
                .treatmentId(300L)
                .prescriptionDate(LocalDate.of(2025, 1, 10))
                .notes("Take medication with food")
                .build();

        patient = Person.builder()
                .personId(100L)
                .firstName("John")
                .lastName("Doe")
                .build();

        doctor = Person.builder()
                .personId(200L)
                .firstName("Dr. Jane")
                .lastName("Smith")
                .build();

        treatment = Treatment.builder()
                .treatmentId(300L)
                .treatmentName("Hypertension Treatment")
                .build();

        prescriptionEntity = Prescription.builder()
                .prescriptionId(1L)
                .patient(patient)
                .doctor(doctor)
                .treatment(treatment)
                .prescriptionDate(LocalDate.of(2025, 1, 10))
                .notes("Take medication with food")
                .build();

        prescriptionDTO = PrescriptionDTO.builder()
                .prescriptionId(1L)
                .patientId(100L)
                .doctorId(200L)
                .treatmentId(300L)
                .prescriptionDate(LocalDate.of(2025, 1, 10))
                .notes("Take medication with food")
                .build();
    }

    @Test
    void createPrescription_ShouldReturnPrescriptionDTO() {
        when(prescriptionDomain.create(request)).thenReturn(prescriptionEntity);
        when(prescriptionMapper.toDTO(prescriptionEntity)).thenReturn(prescriptionDTO);

        PrescriptionDTO result = prescriptionService.create(request);

        assertNotNull(result);
        assertEquals(prescriptionDTO.getPrescriptionId(), result.getPrescriptionId());
        assertEquals(prescriptionDTO.getPatientId(), result.getPatientId());
        assertEquals(prescriptionDTO.getDoctorId(), result.getDoctorId());
        assertEquals(prescriptionDTO.getTreatmentId(), result.getTreatmentId());
        assertEquals(prescriptionDTO.getPrescriptionDate(), result.getPrescriptionDate());
        assertEquals(prescriptionDTO.getNotes(), result.getNotes());
        verify(prescriptionDomain, times(1)).create(request);
        verify(prescriptionMapper, times(1)).toDTO(prescriptionEntity);
    }

    @Test
    void createPrescription_WithNullRequest_ShouldThrowException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> prescriptionService.create(null)
        );

        assertEquals("Prescription request cannot be null", exception.getMessage());
        verify(prescriptionDomain, never()).create(any());
    }

    @Test
    void getById_ShouldReturnPrescriptionDTO() {
        Long prescriptionId = 1L;
        when(prescriptionDomain.getById(prescriptionId)).thenReturn(prescriptionEntity);
        when(prescriptionMapper.toDTO(prescriptionEntity)).thenReturn(prescriptionDTO);

        PrescriptionDTO result = prescriptionService.getById(prescriptionId);

        assertNotNull(result);
        assertEquals(prescriptionDTO.getPrescriptionId(), result.getPrescriptionId());
        assertEquals(prescriptionDTO.getPatientId(), result.getPatientId());
        assertEquals(prescriptionDTO.getDoctorId(), result.getDoctorId());
        assertEquals(prescriptionDTO.getTreatmentId(), result.getTreatmentId());
        assertEquals(prescriptionDTO.getNotes(), result.getNotes());
        verify(prescriptionDomain, times(1)).getById(prescriptionId);
        verify(prescriptionMapper, times(1)).toDTO(prescriptionEntity);
    }

    @Test
    void getById_WithNullId_ShouldThrowException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> prescriptionService.getById(null)
        );

        assertEquals("Prescription ID cannot be null", exception.getMessage());
        verify(prescriptionDomain, never()).getById(any());
    }

    @Test
    void getAll_ShouldReturnListOfPrescriptionDTOs() {
        Person patient2 = Person.builder()
                .personId(101L)
                .firstName("Alice")
                .lastName("Johnson")
                .build();

        Person doctor2 = Person.builder()
                .personId(201L)
                .firstName("Dr. Bob")
                .lastName("Williams")
                .build();

        Treatment treatment2 = Treatment.builder()
                .treatmentId(301L)
                .treatmentName("Diabetes Treatment")
                .build();

        Prescription prescription2 = Prescription.builder()
                .prescriptionId(2L)
                .patient(patient2)
                .doctor(doctor2)
                .treatment(treatment2)
                .prescriptionDate(LocalDate.of(2025, 1, 11))
                .notes("Take before bedtime")
                .build();

        PrescriptionDTO prescriptionDTO2 = PrescriptionDTO.builder()
                .prescriptionId(2L)
                .patientId(101L)
                .doctorId(201L)
                .treatmentId(301L)
                .prescriptionDate(LocalDate.of(2025, 1, 11))
                .notes("Take before bedtime")
                .build();

        Prescription prescription3 = Prescription.builder()
                .prescriptionId(3L)
                .patient(patient)
                .doctor(doctor)
                .treatment(treatment)
                .prescriptionDate(LocalDate.of(2025, 1, 12))
                .notes("Take with water")
                .build();

        PrescriptionDTO prescriptionDTO3 = PrescriptionDTO.builder()
                .prescriptionId(3L)
                .patientId(100L)
                .doctorId(200L)
                .treatmentId(300L)
                .prescriptionDate(LocalDate.of(2025, 1, 12))
                .notes("Take with water")
                .build();

        List<Prescription> prescriptions = List.of(prescriptionEntity, prescription2, prescription3);
        when(prescriptionDomain.getAll()).thenReturn(prescriptions);
        when(prescriptionMapper.toDTO(prescriptionEntity)).thenReturn(prescriptionDTO);
        when(prescriptionMapper.toDTO(prescription2)).thenReturn(prescriptionDTO2);
        when(prescriptionMapper.toDTO(prescription3)).thenReturn(prescriptionDTO3);

        List<PrescriptionDTO> result = prescriptionService.getAll();

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(prescriptionDTO.getPrescriptionId(), result.get(0).getPrescriptionId());
        assertEquals(prescriptionDTO.getPatientId(), result.get(0).getPatientId());
        assertEquals(prescriptionDTO2.getPrescriptionId(), result.get(1).getPrescriptionId());
        assertEquals(prescriptionDTO2.getPatientId(), result.get(1).getPatientId());
        assertEquals(prescriptionDTO3.getPrescriptionId(), result.get(2).getPrescriptionId());
        verify(prescriptionDomain, times(1)).getAll();
        verify(prescriptionMapper, times(3)).toDTO(any(Prescription.class));
    }

    @Test
    void update_ShouldReturnUpdatedPrescriptionDTO() {
        Long prescriptionId = 1L;

        PrescriptionRequestDTO updateRequest = PrescriptionRequestDTO.builder()
                .patientId(100L)
                .doctorId(200L)
                .treatmentId(300L)
                .prescriptionDate(LocalDate.of(2025, 1, 10))
                .notes("Updated: Take medication after meals")
                .build();

        Prescription updatedEntity = Prescription.builder()
                .prescriptionId(prescriptionId)
                .patient(patient)
                .doctor(doctor)
                .treatment(treatment)
                .prescriptionDate(LocalDate.of(2025, 1, 10))
                .notes("Updated: Take medication after meals")
                .build();

        PrescriptionDTO updatedDTO = PrescriptionDTO.builder()
                .prescriptionId(prescriptionId)
                .patientId(100L)
                .doctorId(200L)
                .treatmentId(300L)
                .prescriptionDate(LocalDate.of(2025, 1, 10))
                .notes("Updated: Take medication after meals")
                .build();

        when(prescriptionDomain.update(prescriptionId, updateRequest)).thenReturn(updatedEntity);
        when(prescriptionMapper.toDTO(updatedEntity)).thenReturn(updatedDTO);

        PrescriptionDTO result = prescriptionService.update(prescriptionId, updateRequest);

        assertNotNull(result);
        assertEquals(prescriptionId, result.getPrescriptionId());
        assertEquals("Updated: Take medication after meals", result.getNotes());
        assertEquals(100L, result.getPatientId());
        assertEquals(200L, result.getDoctorId());
        verify(prescriptionDomain, times(1)).update(prescriptionId, updateRequest);
        verify(prescriptionMapper, times(1)).toDTO(updatedEntity);
    }

    @Test
    void update_WithNullId_ShouldThrowException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> prescriptionService.update(null, request)
        );

        assertEquals("Prescription ID cannot be null", exception.getMessage());
        verify(prescriptionDomain, never()).update(any(), any());
    }

    @Test
    void update_WithNullRequest_ShouldThrowException() {
        Long prescriptionId = 1L;
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> prescriptionService.update(prescriptionId, null)
        );

        assertEquals("Prescription request cannot be null", exception.getMessage());
        verify(prescriptionDomain, never()).update(any(), any());
    }

    @Test
    void delete_ShouldCallDomainDelete() {
        Long prescriptionId = 1L;
        doNothing().when(prescriptionDomain).delete(prescriptionId);

        prescriptionService.delete(prescriptionId);

        verify(prescriptionDomain, times(1)).delete(prescriptionId);
    }

    @Test
    void delete_WithNullId_ShouldThrowException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> prescriptionService.delete(null)
        );

        assertEquals("Prescription ID cannot be null", exception.getMessage());
        verify(prescriptionDomain, never()).delete(any());
    }
}