package com.mpp.pharmacy.Services;

import com.mpp.pharmacy.DTO.PurchaseDTO;
import com.mpp.pharmacy.Domain.PurchaseDomain;
import com.mpp.pharmacy.Entity.Purchase;
import com.mpp.pharmacy.Exception.InvalidRequestException;
import com.mpp.pharmacy.Mapper.PurchaseMapper;
import com.mpp.pharmacy.Repository.PurchaseRepository;
import com.mpp.pharmacy.RequestDTO.PurchaseRequestDTO;
import com.mpp.pharmacy.ServiceInterface.PurchaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseMapper mapper;
    private final PurchaseDomain purchaseDomain;

    @Override
    public PurchaseDTO create(PurchaseRequestDTO request) {
        if (request == null) {
            throw new InvalidRequestException("Purchase request cannot be null");
        }

        Purchase created = purchaseDomain.create(request);
        return mapper.toDTO(created);
    }

    @Override
    public PurchaseDTO getById(Long id) {
        if (id == null) {
            throw new InvalidRequestException("Purchase ID cannot be null");
        }

        Purchase purchase = purchaseDomain.getById(id);
        return mapper.toDTO(purchase);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PurchaseDTO> getAll() {
        return purchaseDomain.getAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PurchaseDTO> getByPatient(Long patientId) {
        if (patientId == null) {
            throw new InvalidRequestException("Patient ID cannot be null");
        }

        return purchaseDomain.getByPatient(patientId)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PurchaseDTO> getByPharmacy(Long pharmacyId) {
        if (pharmacyId == null) {
            throw new InvalidRequestException("Pharmacy ID cannot be null");
        }

        return purchaseDomain.getByPharmacy(pharmacyId)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public PurchaseDTO update(Long id, PurchaseRequestDTO request) {
        if (id == null) {
            throw new InvalidRequestException("Purchase ID cannot be null");
        }
        if (request == null) {
            throw new InvalidRequestException("Purchase request cannot be null");
        }

        Purchase updated = purchaseDomain.update(id, request);
        return mapper.toDTO(updated);
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new InvalidRequestException("Purchase ID cannot be null");
        }

        purchaseDomain.delete(id);
    }
}