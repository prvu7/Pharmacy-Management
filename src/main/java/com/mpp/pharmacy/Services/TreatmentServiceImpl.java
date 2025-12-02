package com.mpp.pharmacy.Services;

import com.mpp.pharmacy.DTO.TreatmentDTO;
import com.mpp.pharmacy.Entity.Person;
import com.mpp.pharmacy.Entity.Treatment;
import com.mpp.pharmacy.Exception.ResourceNotFoundException;
import com.mpp.pharmacy.Mapper.TreatmentMapper;
import com.mpp.pharmacy.Repository.PersonRepository;
import com.mpp.pharmacy.Repository.TreatmentRepository;
import com.mpp.pharmacy.RequestDTO.TreatmentRequestDTO;
import com.mpp.pharmacy.ServiceInterface.TreatmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TreatmentServiceImpl implements TreatmentService {

    private final TreatmentRepository repository;
    private final TreatmentMapper mapper;
    private final PersonRepository personRepository;

    @Override
    public TreatmentDTO create(TreatmentRequestDTO request) {
        log.info("Creating treatment: {}", request);

        Person doctor = null;
        if (request.getDoctorId() != null) {
            doctor = personRepository.findById(request.getDoctorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Doctor not found: " + request.getDoctorId()));
        }

        Treatment treatment = mapper.toEntity(request);
        treatment.setDoctor(doctor);

        Treatment saved = repository.save(treatment);
        return mapper.toDTO(saved);
    }

    @Override
    public TreatmentDTO getById(Long id) {
        Treatment entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Treatment not found: " + id));
        return mapper.toDTO(entity);
    }

    @Override
    public List<TreatmentDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TreatmentDTO update(Long id, TreatmentRequestDTO request) {
        Treatment existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Treatment not found: " + id));

        Person doctor = null;
        if (request.getDoctorId() != null) {
            doctor = personRepository.findById(request.getDoctorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Doctor not found: " + request.getDoctorId()));
        }

        existing.setTreatmentName(request.getTreatmentName());
        existing.setDescription(request.getDescription());
        existing.setDoctor(doctor);
        existing.setStartDate(request.getStartDate());
        existing.setEndDate(request.getEndDate());

        return mapper.toDTO(repository.save(existing));
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Treatment not found: " + id);
        }
        repository.deleteById(id);
    }
}