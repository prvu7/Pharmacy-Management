package com.mpp.pharmacy.Domain;

import java.util.List;

import com.mpp.pharmacy.Entity.Drug;
import com.mpp.pharmacy.Entity.Purchase;
import com.mpp.pharmacy.Entity.PurchaseDetailId;
import com.mpp.pharmacy.Entity.Purchase_Detail;
import com.mpp.pharmacy.Exception.DuplicateResourceException;
import com.mpp.pharmacy.Exception.InvalidRequestException;
import com.mpp.pharmacy.Exception.ResourceNotFoundException;
import com.mpp.pharmacy.Repository.DrugRepository;
import com.mpp.pharmacy.Repository.PurchaseDetailRepository;
import com.mpp.pharmacy.Repository.PurchaseRepository;
import com.mpp.pharmacy.RequestDTO.PurchaseDetailRequestDTO;
import com.mpp.pharmacy.Validators.PurchaseDetailValidator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PurchaseDetailDomain {

    private final PurchaseDetailRepository repository;
    private final PurchaseRepository purchaseRepository;
    private final DrugRepository drugRepository;
    private final PurchaseDetailValidator validator;

    public Purchase_Detail create(PurchaseDetailRequestDTO request) {

        Purchase purchase = validateAndFetchPurchase(request.getPurchaseId());
        Drug drug = validateAndFetchDrug(request.getDrugId());

        PurchaseDetailId id = new PurchaseDetailId(request.getPurchaseId(), request.getDrugId());
        if (repository.existsById(id)) {
            throw new DuplicateResourceException(
                    String.format("Purchase detail already exists for purchase %d and drug %d",
                            request.getPurchaseId(), request.getDrugId())
            );
        }

        Purchase_Detail detail = Purchase_Detail.builder()
                .purchase(purchase)
                .drug(drug)
                .quantity(request.getQuantity())
                .unitPrice(request.getUnitPrice())
                .build();

        validator.validateForCreation(detail);

        return repository.save(detail);
    }

    public List<Purchase_Detail> getByDrug(Long drugId) {
        return repository.findByDrug_DrugId(drugId);
    }

    public List<Purchase_Detail> getAll() {
        return repository.findAll();
    }

    public List<Purchase_Detail> getByPurchase(Long purchaseId) {
        return repository.findByPurchase_PurchaseId(purchaseId);
    }

    public Purchase_Detail update(Long purchaseId, Long drugId, PurchaseDetailRequestDTO request) {

        PurchaseDetailId id = new PurchaseDetailId(purchaseId, drugId);
        Purchase_Detail existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase detail not found"));

        existing.setQuantity(request.getQuantity());
        existing.setUnitPrice(request.getUnitPrice());

        validator.validateForUpdate(existing);

        return repository.save(existing);
    }

    public Purchase_Detail getById(Long purchaseId, Long drugId) {
        PurchaseDetailId id = new PurchaseDetailId(purchaseId, drugId);
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase detail not found"));
    }

    public void delete(Long purchaseId, Long drugId) {

        PurchaseDetailId id = new PurchaseDetailId(purchaseId, drugId);
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Purchase detail not found");
        }

        repository.deleteById(id);
    }

    private Purchase validateAndFetchPurchase(Long purchaseId) {
        if (purchaseId == null) {
            throw new InvalidRequestException("Purchase ID is required");
        }

        return purchaseRepository.findById(purchaseId)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase not found: " + purchaseId));
    }

    private Drug validateAndFetchDrug(Long drugId) {
        if (drugId == null) {
            throw new InvalidRequestException("Drug ID is required");
        }

        return drugRepository.findById(drugId)
                .orElseThrow(() -> new ResourceNotFoundException("Drug not found: " + drugId));
    }
}