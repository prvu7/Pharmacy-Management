package com.mpp.pharmacy.Services;

import com.mpp.pharmacy.DTO.PrescriptionDTO;
import com.mpp.pharmacy.Entity.Person;
import com.mpp.pharmacy.Entity.Prescription;
import com.mpp.pharmacy.Entity.Treatment;
import com.mpp.pharmacy.Exception.ResourceNotFoundException;
import com.mpp.pharmacy.Mapper.PrescriptionMapper;
import com.mpp.pharmacy.Repository.PersonRepository;
import com.mpp.pharmacy.Repository.PrescriptionRepository;
import com.mpp.pharmacy.Repository.TreatmentRepository;
import com.mpp.pharmacy.RequestDTO.PrescriptionRequestDTO;
import com.mpp.pharmacy.ServiceInterface.PrescriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrescriptionServiceImpl implements PrescriptionService {

    private final PrescriptionRepository repository;
    private final PrescriptionMapper mapper;
    private final PersonRepository personRepository;
    private final TreatmentRepository treatmentRepository;

    @Override
    public PrescriptionDTO create(PrescriptionRequestDTO request) {
        log.info("Creating prescription: {}", request);

        Person patient = personRepository.findById(request.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found: " + request.getPatientId()));

        Person doctor = personRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found: " + request.getDoctorId()));

        Treatment treatment = null;
        if (request.getTreatmentId() != null) {
            treatment = treatmentRepository.findById(request.getTreatmentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Treatment not found: " + request.getTreatmentId()));
        }

        Prescription prescription = mapper.toEntity(request);
        prescription.setPatient(patient);
        prescription.setDoctor(doctor);
        prescription.setTreatment(treatment);

        Prescription saved = repository.save(prescription);
        return mapper.toDTO(saved);
    }

    @Override
    public PrescriptionDTO getById(Long id) {
        Prescription entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Prescription not found: " + id));
        return mapper.toDTO(entity);
    }

    @Override
    public List<PrescriptionDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PrescriptionDTO update(Long id, PrescriptionRequestDTO request) {
        Prescription existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Prescription not found: " + id));

        Person patient = personRepository.findById(request.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found: " + request.getPatientId()));

        Person doctor = personRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found: " + request.getDoctorId()));

        Treatment treatment = null;
        if (request.getTreatmentId() != null) {
            treatment = treatmentRepository.findById(request.getTreatmentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Treatment not found: " + request.getTreatmentId()));
        }

        existing.setPatient(patient);
        existing.setDoctor(doctor);
        existing.setTreatment(treatment);
        existing.setPrescriptionDate(request.getPrescriptionDate());
        existing.setNotes(request.getNotes());

        return mapper.toDTO(repository.save(existing));
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Prescription not found: " + id);
        }
        repository.deleteById(id);
    }
}