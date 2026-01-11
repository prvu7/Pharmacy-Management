package com.mpp.pharmacy.Domain;

import com.mpp.pharmacy.Entity.Drug;
import com.mpp.pharmacy.Entity.Inventory;
import com.mpp.pharmacy.Entity.Pharmacy;
import com.mpp.pharmacy.Exception.DuplicateResourceException;
import com.mpp.pharmacy.Exception.InvalidRequestException;
import com.mpp.pharmacy.Exception.ResourceNotFoundException;
import com.mpp.pharmacy.Repository.DrugRepository;
import com.mpp.pharmacy.Repository.InventoryRepository;
import com.mpp.pharmacy.Repository.PharmacyRepository;
import com.mpp.pharmacy.RequestDTO.InventoryRequestDTO;
import com.mpp.pharmacy.Validators.InventoryValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class InventoryDomain {

    private final InventoryRepository inventoryRepository;
    private final PharmacyRepository pharmacyRepository;
    private final DrugRepository drugRepository;
    private final InventoryValidator validator;

    public Inventory create(InventoryRequestDTO request) {
        Pharmacy pharmacy = validateAndFetchPharmacy(request.getPharmacyId());
        Drug drug = validateAndFetchDrug(request.getDrugId());

        Optional<Inventory> existingInventory = inventoryRepository
                .findByPharmacy_PharmacyIdAndDrug_DrugId(request.getPharmacyId(), request.getDrugId());

        if (existingInventory.isPresent()) {
            throw new DuplicateResourceException(
                    String.format("Inventory already exists for pharmacy %d and drug %d",
                            request.getPharmacyId(), request.getDrugId())
            );
        }

        Inventory inventory = Inventory.builder()
                .pharmacy(pharmacy)
                .drug(drug)
                .quantityInStock(request.getQuantityInStock())
                .expiryDate(request.getExpiryDate())
                .build();

        validator.validateForCreation(inventory);

        return inventoryRepository.save(inventory);
    }

    public Inventory update(Long id, InventoryRequestDTO request) {

        Inventory existing = inventoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found: " + id));

        Pharmacy pharmacy = validateAndFetchPharmacy(request.getPharmacyId());
        Drug drug = validateAndFetchDrug(request.getDrugId());

        Optional<Inventory> duplicateCheck = inventoryRepository
                .findByPharmacy_PharmacyIdAndDrug_DrugId(request.getPharmacyId(), request.getDrugId());

        if (duplicateCheck.isPresent() && !duplicateCheck.get().getInventoryId().equals(id)) {
            throw new DuplicateResourceException(
                    String.format("Inventory already exists for pharmacy %d and drug %d",
                            request.getPharmacyId(), request.getDrugId())
            );
        }

        existing.setPharmacy(pharmacy);
        existing.setDrug(drug);
        existing.setQuantityInStock(request.getQuantityInStock());
        existing.setExpiryDate(request.getExpiryDate());

        validator.validateForUpdate(existing);

        return inventoryRepository.save(existing);
    }

    public List<Inventory> getByPharmacy(Long pharmacyId) {
        return inventoryRepository.findByPharmacy_PharmacyId(pharmacyId);
    }

    public List<Inventory> getByDrug(Long drugId) {
        return inventoryRepository.findByDrug_DrugId(drugId);
    }

    public Inventory getById(Long id) {
        return inventoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found: " + id));
    }

    public void delete(Long id) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found: " + id));

        inventoryRepository.deleteById(id);
    }

    public void adjustStock(Long inventoryId, Integer quantityChange) {
        Inventory inventory = getById(inventoryId);
        Integer newQuantity = inventory.getQuantityInStock() + quantityChange;

        if (newQuantity < 0) {
            throw new InvalidRequestException("Insufficient stock for this operation");
        }

        inventory.setQuantityInStock(newQuantity);
        validator.validateForUpdate(inventory);
        inventoryRepository.save(inventory);
    }

    public List<Inventory> getAll() {
        return inventoryRepository.findAll();
    }

    private Pharmacy validateAndFetchPharmacy(Long pharmacyId) {
        if (pharmacyId == null) {
            throw new InvalidRequestException("Pharmacy ID is required");
        }

        return pharmacyRepository.findById(pharmacyId)
                .orElseThrow(() -> new ResourceNotFoundException("Pharmacy not found: " + pharmacyId));
    }

    private Drug validateAndFetchDrug(Long drugId) {
        if (drugId == null) {
            throw new InvalidRequestException("Drug ID is required");
        }

        return drugRepository.findById(drugId)
                .orElseThrow(() -> new ResourceNotFoundException("Drug not found: " + drugId));
    }
}