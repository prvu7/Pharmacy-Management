package com.mpp.pharmacy.Services;

import com.mpp.pharmacy.DTO.PrescriptionDTO;
import com.mpp.pharmacy.Domain.PrescriptionDomain;
import com.mpp.pharmacy.Entity.Prescription;
import com.mpp.pharmacy.Exception.InvalidRequestException;
import com.mpp.pharmacy.Mapper.PrescriptionMapper;
import com.mpp.pharmacy.Repository.PrescriptionRepository;
import com.mpp.pharmacy.RequestDTO.PrescriptionRequestDTO;
import com.mpp.pharmacy.ServiceInterface.PrescriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PrescriptionServiceImpl implements PrescriptionService {

    private final PrescriptionRepository repository;
    private final PrescriptionMapper mapper;
    private final PrescriptionDomain prescriptionDomain;

    @Override
    public PrescriptionDTO create(PrescriptionRequestDTO request) {
        log.info("Service: Creating prescription");

        if (request == null) {
            throw new InvalidRequestException("Prescription request cannot be null");
        }

        Prescription created = prescriptionDomain.create(request);
        return mapper.toDTO(created);
    }

    @Override
    public PrescriptionDTO getById(Long id) {
        log.debug("Service: Fetching prescription with id: {}", id);

        if (id == null) {
            throw new InvalidRequestException("Prescription ID cannot be null");
        }

        Prescription prescription = prescriptionDomain.getById(id);
        return mapper.toDTO(prescription);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PrescriptionDTO> getAll() {
        log.debug("Service: Fetching all prescriptions");
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public PrescriptionDTO update(Long id, PrescriptionRequestDTO request) {
        log.info("Service: Updating prescription with id: {}", id);

        if (id == null) {
            throw new InvalidRequestException("Prescription ID cannot be null");
        }
        if (request == null) {
            throw new InvalidRequestException("Prescription request cannot be null");
        }

        Prescription updated = prescriptionDomain.update(id, request);
        return mapper.toDTO(updated);
    }

    @Override
    public void delete(Long id) {
        log.info("Service: Deleting prescription with id: {}", id);

        if (id == null) {
            throw new InvalidRequestException("Prescription ID cannot be null");
        }

        prescriptionDomain.delete(id);
    }
}