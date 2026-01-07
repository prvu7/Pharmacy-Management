package com.mpp.pharmacy.Domains;

import com.mpp.pharmacy.Domain.PrescriptionDomain;
import com.mpp.pharmacy.Entity.Person;
import com.mpp.pharmacy.Entity.Prescription;
import com.mpp.pharmacy.Entity.Treatment;
import com.mpp.pharmacy.Enum.Role;
import com.mpp.pharmacy.Enum.Sex;
import com.mpp.pharmacy.Repository.PersonRepository;
import com.mpp.pharmacy.Repository.PrescriptionRepository;
import com.mpp.pharmacy.Repository.TreatmentRepository;
import com.mpp.pharmacy.RequestDTO.PrescriptionRequestDTO;
import com.mpp.pharmacy.Validators.PrescriptionValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PrescriptionDomainTest {

    @Mock
    private PrescriptionRepository repository;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private TreatmentRepository treatmentRepository;

    @Mock
    private PrescriptionValidator validator;

    @InjectMocks
    private PrescriptionDomain prescriptionDomain;

    @Test
    void create_ShouldReturnCreatedPrescription() {
        // Arrange
        PrescriptionRequestDTO request = PrescriptionRequestDTO.builder()
                .patientId(1L)
                .doctorId(2L)
                .treatmentId(3L)
                .prescriptionDate(LocalDate.of(2026, 1, 5))
                .notes("Take medication twice daily after meals")
                .build();

        Person patient = Person.builder()
                .personId(1L)
                .firstName("John")
                .lastName("Doe")
                .sex(Sex.M)
                .dateOfBirth(LocalDate.of(1985, 5, 15))
                .phone("+1234567890")
                .email("john.doe@example.com")
                .address("123 Main Street")
                .role(Role.patient)
                .build();

        Person doctor = Person.builder()
                .personId(2L)
                .firstName("Dr. Jane")
                .lastName("Smith")
                .sex(Sex.F)
                .dateOfBirth(LocalDate.of(1980, 3, 20))
                .phone("+0987654321")
                .email("jane.smith@hospital.com")
                .address("456 Medical Center")
                .role(Role.doctor)
                .build();

        Treatment treatment = Treatment.builder()
                .treatmentId(3L)
                .treatmentName("Antibiotic Therapy")
                .description("10-day course of antibiotics")
                .doctor(doctor)
                .startDate(LocalDate.of(2026, 1, 5))
                .endDate(LocalDate.of(2026, 1, 15))
                .build();

        Prescription savedPrescription = Prescription.builder()
                .prescriptionId(1L)
                .patient(patient)
                .doctor(doctor)
                .treatment(treatment)
                .prescriptionDate(LocalDate.of(2026, 1, 5))
                .notes("Take medication twice daily after meals")
                .build();

        when(personRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(personRepository.findById(2L)).thenReturn(Optional.of(doctor));
        when(treatmentRepository.findById(3L)).thenReturn(Optional.of(treatment));
        doNothing().when(validator).validateForCreation(any(Prescription.class));
        when(repository.save(any(Prescription.class))).thenReturn(savedPrescription);

        // Act
        Prescription result = prescriptionDomain.create(request);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getPrescriptionId());
        assertEquals(patient.getPersonId(), result.getPatient().getPersonId());
        assertEquals(doctor.getPersonId(), result.getDoctor().getPersonId());
        assertEquals(treatment.getTreatmentId(), result.getTreatment().getTreatmentId());
        assertEquals(LocalDate.of(2026, 1, 5), result.getPrescriptionDate());
        assertEquals("Take medication twice daily after meals", result.getNotes());

        // Verify interactions
        verify(personRepository, times(1)).findById(1L);
        verify(personRepository, times(1)).findById(2L);
        verify(treatmentRepository, times(1)).findById(3L);
        verify(validator, times(1)).validateForCreation(any(Prescription.class));
        verify(repository, times(1)).save(any(Prescription.class));
    }
}
