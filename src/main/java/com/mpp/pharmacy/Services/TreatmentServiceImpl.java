package com.mpp.pharmacy.Services;

import com.mpp.pharmacy.DTO.TreatmentDTO;
import com.mpp.pharmacy.Domain.TreatmentDomain;
import com.mpp.pharmacy.Entity.Treatment;
import com.mpp.pharmacy.Exception.InvalidRequestException;
import com.mpp.pharmacy.Mapper.TreatmentMapper;
import com.mpp.pharmacy.Repository.TreatmentRepository;
import com.mpp.pharmacy.RequestDTO.TreatmentRequestDTO;
import com.mpp.pharmacy.ServiceInterface.TreatmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TreatmentServiceImpl implements TreatmentService {

    private final TreatmentRepository repository;
    private final TreatmentMapper mapper;
    private final TreatmentDomain treatmentDomain;

    @Override
    public TreatmentDTO create(TreatmentRequestDTO request) {
        log.info("Service: Creating treatment");

        if (request == null) {
            throw new InvalidRequestException("Treatment request cannot be null");
        }

        Treatment created = treatmentDomain.create(request);
        return mapper.toDTO(created);
    }

    @Override
    public TreatmentDTO getById(Long id) {
        log.debug("Service: Fetching treatment with id: {}", id);

        if (id == null) {
            throw new InvalidRequestException("Treatment ID cannot be null");
        }

        Treatment treatment = treatmentDomain.getById(id);
        return mapper.toDTO(treatment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TreatmentDTO> getAll() {
        log.debug("Service: Fetching all treatments");
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public TreatmentDTO update(Long id, TreatmentRequestDTO request) {
        log.info("Service: Updating treatment with id: {}", id);

        if (id == null) {
            throw new InvalidRequestException("Treatment ID cannot be null");
        }
        if (request == null) {
            throw new InvalidRequestException("Treatment request cannot be null");
        }

        Treatment updated = treatmentDomain.update(id, request);
        return mapper.toDTO(updated);
    }

    @Override
    public void delete(Long id) {
        log.info("Service: Deleting treatment with id: {}", id);

        if (id == null) {
            throw new InvalidRequestException("Treatment ID cannot be null");
        }

        treatmentDomain.delete(id);
    }
}