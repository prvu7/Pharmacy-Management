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
import com.mpp.pharmacy.Domain.PrescriptionDomain;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrescriptionServiceImpl implements PrescriptionService {

    private final PrescriptionDomain domain;
    private final PrescriptionMapper mapper;

    @Override
    public PrescriptionDTO create(PrescriptionRequestDTO request) {
        if (request == null) {
            throw new IllegalArgumentException("Prescription request cannot be null");
        }

        Prescription entity = domain.create(request);

        return mapper.toDTO(entity);
    }

    @Override
    public PrescriptionDTO getById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Prescription ID cannot be null");
        }

        Prescription response = domain.getById(id);

        return mapper.toDTO(response);
    }

    @Override
    public List<PrescriptionDTO> getAll() {
        return domain.getAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PrescriptionDTO update(Long id, PrescriptionRequestDTO request) {
        if (id == null) {
            throw new IllegalArgumentException("Prescription ID cannot be null");
        }
        if (request == null) {
            throw new IllegalArgumentException("Prescription request cannot be null");
        }

        Prescription updatedEntity = domain.update(id, request);

        return mapper.toDTO(updatedEntity);
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Prescription ID cannot be null");
        }

        domain.delete(id);
    }
}