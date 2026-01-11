package com.mpp.pharmacy.Services;
import com.mpp.pharmacy.DTO.PharmacyDTO;
import com.mpp.pharmacy.Entity.Pharmacy;
import com.mpp.pharmacy.Exception.ResourceNotFoundException;
import com.mpp.pharmacy.Mapper.PharmacyMapper;
import com.mpp.pharmacy.Repository.PharmacyRepository;
import com.mpp.pharmacy.RequestDTO.PharmacyRequestDTO;
import com.mpp.pharmacy.ServiceInterface.PharmacyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.mpp.pharmacy.Domain.PharmacyDomain;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PharmacyServiceImpl implements PharmacyService {

    private final PharmacyDomain domain;
    private final PharmacyMapper mapper;

    @Override
    public PharmacyDTO create(PharmacyRequestDTO request) {
        if (request == null) {
            throw new IllegalArgumentException("Pharmacy request cannot be null");
        }

        Pharmacy pharmacy = domain.create(request);

        return mapper.toDTO(pharmacy);
    }

    @Override
    public PharmacyDTO getById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Pharmacy ID cannot be null");
        }

        Pharmacy pharmacy = domain.getById(id);

        return mapper.toDTO(pharmacy);
    }

    @Override
    public List<PharmacyDTO> getAll() {
        return domain.getAll().stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public PharmacyDTO update(Long id, PharmacyRequestDTO request) {
        if (id == null) {
            throw new IllegalArgumentException("Pharmacy ID cannot be null");
        }
        if (request == null) {
            throw new IllegalArgumentException("Pharmacy request cannot be null");
        }

        Pharmacy updatedPharmacy = domain.update(id, request);

        return mapper.toDTO(updatedPharmacy);
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Pharmacy ID cannot be null");
        }

        domain.delete(id);
    }
}