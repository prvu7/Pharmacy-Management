package com.mpp.pharmacy.Domain;

import com.mpp.pharmacy.Entity.Person;
import com.mpp.pharmacy.Entity.Prescription;
import com.mpp.pharmacy.Entity.Treatment;
import com.mpp.pharmacy.Enum.Role;
import com.mpp.pharmacy.Exception.InvalidRequestException;
import com.mpp.pharmacy.Exception.ResourceNotFoundException;
import com.mpp.pharmacy.Repository.PersonRepository;
import com.mpp.pharmacy.Repository.PrescriptionRepository;
import com.mpp.pharmacy.Repository.TreatmentRepository;
import com.mpp.pharmacy.RequestDTO.PrescriptionRequestDTO;
import com.mpp.pharmacy.Validators.PrescriptionValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PrescriptionDomain {

    private final PrescriptionRepository repository;
    private final PersonRepository personRepository;
    private final TreatmentRepository treatmentRepository;
    private final PrescriptionValidator validator;

    public Prescription create(PrescriptionRequestDTO request) {
        log.debug("Domain: Creating prescription");

        Person patient = validateAndFetchPatient(request.getPatientId());
        Person doctor = validateAndFetchDoctor(request.getDoctorId());
        Treatment treatment = validateAndFetchTreatment(request.getTreatmentId());

        if (treatment != null) {
            validateTreatmentOwnership(treatment, patient, doctor);
        }

        Prescription prescription = Prescription.builder()
                .patient(patient)
                .doctor(doctor)
                .treatment(treatment)
                .prescriptionDate(request.getPrescriptionDate())
                .notes(request.getNotes())
                .build();

        validator.validateForCreation(prescription);

        log.info("Domain: Prescription validation passed, saving to database");
        return repository.save(prescription);
    }

    public Prescription update(Long id, PrescriptionRequestDTO request) {
        log.debug("Domain: Updating prescription with id: {}", id);

        Prescription existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Prescription not found: " + id));

        Person patient = validateAndFetchPatient(request.getPatientId());
        Person doctor = validateAndFetchDoctor(request.getDoctorId());
        Treatment treatment = validateAndFetchTreatment(request.getTreatmentId());

        if (treatment != null) {
            validateTreatmentOwnership(treatment, patient, doctor);
        }

        existing.setPatient(patient);
        existing.setDoctor(doctor);
        existing.setTreatment(treatment);
        existing.setPrescriptionDate(request.getPrescriptionDate());
        existing.setNotes(request.getNotes());

        validator.validateForUpdate(existing);

        log.info("Domain: Prescription update validation passed, saving to database");
        return repository.save(existing);
    }

    public Prescription getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Prescription not found: " + id));
    }

    public void delete(Long id) {
        log.debug("Domain: Deleting prescription with id: {}", id);

        Prescription prescription = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Prescription not found: " + id));

        repository.deleteById(id);
        log.info("Domain: Prescription deleted successfully");
    }

    private Person validateAndFetchPatient(Long patientId) {
        if (patientId == null) {
            throw new InvalidRequestException("Patient ID is required");
        }

        Person patient = personRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found: " + patientId));

        if (patient.getRole() != Role.patient) {
            throw new InvalidRequestException("Person with ID " + patientId + " is not a patient. Role: " + patient.getRole());
        }

        return patient;
    }

    private Person validateAndFetchDoctor(Long doctorId) {
        if (doctorId == null) {
            throw new InvalidRequestException("Doctor ID is required");
        }

        Person doctor = personRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found: " + doctorId));

        if (doctor.getRole() != Role.doctor) {
            throw new InvalidRequestException("Person with ID " + doctorId + " is not a doctor. Role: " + doctor.getRole());
        }

        return doctor;
    }

    private Treatment validateAndFetchTreatment(Long treatmentId) {
        if (treatmentId == null) {
            return null;
        }

        return treatmentRepository.findById(treatmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Treatment not found: " + treatmentId));
    }

    private void validateTreatmentOwnership(Treatment treatment, Person patient, Person doctor) {
        if (treatment.getDoctor() != null &&
                !treatment.getDoctor().getPersonId().equals(doctor.getPersonId())) {
            throw new InvalidRequestException("Treatment does not belong to the specified doctor");
        }
    }
}