package com.mpp.pharmacy.Services;
import com.mpp.pharmacy.DTO.PharmacyDTO;
import com.mpp.pharmacy.Domain.PharmacyDomain;
import com.mpp.pharmacy.Entity.Pharmacy;
import com.mpp.pharmacy.Exception.InvalidRequestException;
import com.mpp.pharmacy.Mapper.PharmacyMapper;
import com.mpp.pharmacy.Repository.PharmacyRepository;
import com.mpp.pharmacy.RequestDTO.PharmacyRequestDTO;
import com.mpp.pharmacy.ServiceInterface.PharmacyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PharmacyServiceImpl implements PharmacyService {

    private final PharmacyRepository repository;
    private final PharmacyMapper mapper;
    private final PharmacyDomain pharmacyDomain;

    @Override
    public PharmacyDTO create(PharmacyRequestDTO request) {
        log.info("Service: Creating pharmacy");

        // Validate request is not null
        if (request == null) {
            throw new InvalidRequestException("Pharmacy request cannot be null");
        }

        Pharmacy created = pharmacyDomain.create(request);
        return mapper.toDTO(created);
    }

    @Override
    public PharmacyDTO getById(Long id) {
        log.debug("Service: Fetching pharmacy with id: {}", id);

        // Validate ID is not null
        if (id == null) {
            throw new InvalidRequestException("Pharmacy ID cannot be null");
        }

        Pharmacy pharmacy = pharmacyDomain.getById(id);
        return mapper.toDTO(pharmacy);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PharmacyDTO> getAll() {
        log.debug("Service: Fetching all pharmacies");
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public PharmacyDTO update(Long id, PharmacyRequestDTO request) {
        log.info("Service: Updating pharmacy with id: {}", id);

        // Validate inputs are not null
        if (id == null) {
            throw new InvalidRequestException("Pharmacy ID cannot be null");
        }
        if (request == null) {
            throw new InvalidRequestException("Pharmacy request cannot be null");
        }

        Pharmacy updated = pharmacyDomain.update(id, request);
        return mapper.toDTO(updated);
    }

    @Override
    public void delete(Long id) {
        log.info("Service: Deleting pharmacy with id: {}", id);

        // Validate ID is not null
        if (id == null) {
            throw new InvalidRequestException("Pharmacy ID cannot be null");
        }

        pharmacyDomain.delete(id);
    }
}