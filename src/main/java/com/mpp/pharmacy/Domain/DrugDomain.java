package com.mpp.pharmacy.Domain;


import com.mpp.pharmacy.Entity.Drug;
import com.mpp.pharmacy.Exception.DuplicateResourceException;
import com.mpp.pharmacy.Exception.ResourceNotFoundException;
import com.mpp.pharmacy.Repository.DrugRepository;
import com.mpp.pharmacy.RequestDTO.DrugRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DrugDomain {

    private final DrugRepository repository;

    public Drug create(DrugRequestDTO request) {
        // Business logic: Check for duplicates
        if (repository.existsByDrugNameIgnoreCase(request.getDrugName())) {
            throw new DuplicateResourceException("Drug already exists: " + request.getDrugName());
        }

        // Create new drug entity
        Drug drug = Drug.builder()
                .drugName(request.getDrugName())
                .genericName(request.getGenericName())
                .description(request.getDescription())
                .dosageForm(request.getDosageForm())
                .manufacturer(request.getManufacturer())
                .price(request.getPrice())
                .build();

        return repository.save(drug);
    }

    public List<Drug> getAll(){
        return repository.findAll();
    }

    public Drug update(Long id, DrugRequestDTO request) {
        // Fetch existing drug
        Drug existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Drug not found: " + id));

        // Business logic: Check for duplicate name (excluding current drug)
        repository.findByDrugNameIgnoreCase(request.getDrugName())
                .ifPresent(drug -> {
                    if (!drug.getDrugId().equals(id)) {
                        throw new DuplicateResourceException("Drug name already exists: " + request.getDrugName());
                    }
                });

        // Update fields
        existing.setDrugName(request.getDrugName());
        existing.setGenericName(request.getGenericName());
        existing.setDescription(request.getDescription());
        existing.setDosageForm(request.getDosageForm());
        existing.setManufacturer(request.getManufacturer());
        existing.setPrice(request.getPrice());

        return repository.save(existing);
    }

    public Drug getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Drug not found: " + id));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Drug not found: " + id);
        }
        repository.deleteById(id);
    }
}
