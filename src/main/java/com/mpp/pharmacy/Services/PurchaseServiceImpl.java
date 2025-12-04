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

    private final PurchaseRepository purchaseRepository;
    private final PurchaseMapper mapper;
    private final PurchaseDomain purchaseDomain;

    @Override
    public PurchaseDTO create(PurchaseRequestDTO request) {
        log.info("Service: Creating purchase");

        if (request == null) {
            throw new InvalidRequestException("Purchase request cannot be null");
        }

        Purchase created = purchaseDomain.create(request);
        return mapper.toDTO(created);
    }

    @Override
    public PurchaseDTO getById(Long id) {
        log.debug("Service: Fetching purchase with id: {}", id);

        if (id == null) {
            throw new InvalidRequestException("Purchase ID cannot be null");
        }

        Purchase purchase = purchaseDomain.getById(id);
        return mapper.toDTO(purchase);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PurchaseDTO> getAll() {
        log.debug("Service: Fetching all purchases");
        return purchaseRepository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PurchaseDTO> getByPatient(Long patientId) {
        log.debug("Service: Fetching purchases for patient: {}", patientId);

        if (patientId == null) {
            throw new InvalidRequestException("Patient ID cannot be null");
        }

        return purchaseRepository.findByPatient_PersonId(patientId)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PurchaseDTO> getByPharmacy(Long pharmacyId) {
        log.debug("Service: Fetching purchases for pharmacy: {}", pharmacyId);

        if (pharmacyId == null) {
            throw new InvalidRequestException("Pharmacy ID cannot be null");
        }

        return purchaseRepository.findByPharmacy_PharmacyId(pharmacyId)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public PurchaseDTO update(Long id, PurchaseRequestDTO request) {
        log.info("Service: Updating purchase with id: {}", id);

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
        log.info("Service: Deleting purchase with id: {}", id);

        if (id == null) {
            throw new InvalidRequestException("Purchase ID cannot be null");
        }

        purchaseDomain.delete(id);
    }
}