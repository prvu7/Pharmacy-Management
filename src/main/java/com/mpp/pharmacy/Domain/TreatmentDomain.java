package com.mpp.pharmacy.Domain;

import com.mpp.pharmacy.Entity.Person;
import com.mpp.pharmacy.Entity.Treatment;
import com.mpp.pharmacy.Enum.Role;
import com.mpp.pharmacy.Exception.InvalidRequestException;
import com.mpp.pharmacy.Exception.ResourceNotFoundException;
import com.mpp.pharmacy.Repository.PersonRepository;
import com.mpp.pharmacy.Repository.TreatmentRepository;
import com.mpp.pharmacy.RequestDTO.TreatmentRequestDTO;
import com.mpp.pharmacy.Validators.TreatmentValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TreatmentDomain {

    private final TreatmentRepository repository;
    private final PersonRepository personRepository;
    private final TreatmentValidator validator;

    public Treatment create(TreatmentRequestDTO request) {

        Person doctor = validateAndFetchDoctor(request.getDoctorId());

        Treatment treatment = Treatment.builder()
                .treatmentName(request.getTreatmentName())
                .description(request.getDescription())
                .doctor(doctor)
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .build();

        validator.validateForCreation(treatment);

        return repository.save(treatment);
    }

    public Treatment update(Long id, TreatmentRequestDTO request) {

        Treatment existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Treatment not found: " + id));

        Person doctor = validateAndFetchDoctor(request.getDoctorId());

        existing.setTreatmentName(request.getTreatmentName());
        existing.setDescription(request.getDescription());
        existing.setDoctor(doctor);
        existing.setStartDate(request.getStartDate());
        existing.setEndDate(request.getEndDate());

        validator.validateForUpdate(existing);

        return repository.save(existing);
    }

    public Treatment getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Treatment not found: " + id));
    }

    public void delete(Long id) {

        Treatment treatment = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Treatment not found: " + id));

        repository.deleteById(id);
    }

    public List<Treatment> getAll() {
        return repository.findAll();
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
}