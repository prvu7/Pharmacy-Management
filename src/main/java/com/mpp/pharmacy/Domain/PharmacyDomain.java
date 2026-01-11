package com.mpp.pharmacy.Domain;

import com.mpp.pharmacy.Entity.Pharmacy;
import com.mpp.pharmacy.Exception.DuplicateResourceException;
import com.mpp.pharmacy.Exception.ResourceNotFoundException;
import com.mpp.pharmacy.Repository.PharmacyRepository;
import com.mpp.pharmacy.RequestDTO.PharmacyRequestDTO;
import com.mpp.pharmacy.Validators.PharmacyValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class PharmacyDomain {

    private final PharmacyRepository repository;
    private final PharmacyValidator validator;

    public Pharmacy create(PharmacyRequestDTO request) {
        // Create entity from request
        Pharmacy pharmacy = Pharmacy.builder()
                .name(request.getName())
                .address(request.getAddress())
                .phone(request.getPhone())
                .build();

        // Validate all fields for creation
        validator.validateForCreation(pharmacy);

        // Business rule: Check for duplicate pharmacy name
        if (repository.existsByNameIgnoreCase(request.getName())) {
            throw new DuplicateResourceException("Pharmacy with this name already exists: " + request.getName());
        }

        // Business rule: Check for duplicate phone number
        if (repository.existsByPhone(request.getPhone())) {
            throw new DuplicateResourceException("Pharmacy with this phone number already exists: " + request.getPhone());
        }

        // Business rule: Check for duplicate address (optional, depending on requirements)
        repository.findByAddressIgnoreCase(request.getAddress())
                .ifPresent(existing -> {
                    throw new DuplicateResourceException("Pharmacy at this address already exists: " + request.getAddress());
                });

        return repository.save(pharmacy);
    }

    public Pharmacy update(Long id, PharmacyRequestDTO request) {
        // Fetch existing pharmacy
        Pharmacy existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pharmacy not found: " + id));

        // Update fields
        existing.setName(request.getName());
        existing.setAddress(request.getAddress());
        existing.setPhone(request.getPhone());

        // Validate updated entity
        validator.validateForUpdate(existing);

        // Business rule: Check for duplicate name (excluding current pharmacy)
        repository.findByNameIgnoreCase(request.getName())
                .ifPresent(pharmacy -> {
                    if (!pharmacy.getPharmacyId().equals(id)) {
                        throw new DuplicateResourceException("Pharmacy with this name already exists: " + request.getName());
                    }
                });

        // Business rule: Check for duplicate phone (excluding current pharmacy)
        repository.findByPhone(request.getPhone())
                .ifPresent(pharmacy -> {
                    if (!pharmacy.getPharmacyId().equals(id)) {
                        throw new DuplicateResourceException("Pharmacy with this phone number already exists: " + request.getPhone());
                    }
                });

        // Business rule: Check for duplicate address (excluding current pharmacy)
        repository.findByAddressIgnoreCase(request.getAddress())
                .ifPresent(pharmacy -> {
                    if (!pharmacy.getPharmacyId().equals(id)) {
                        throw new DuplicateResourceException("Pharmacy at this address already exists: " + request.getAddress());
                    }
                });

        return repository.save(existing);
    }

    public Pharmacy getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pharmacy not found: " + id));
    }

    public void delete(Long id) {
        Pharmacy pharmacy = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pharmacy not found: " + id));

        // Business rule: Check if pharmacy can be deleted
        // Example: Check if pharmacy has active inventory, prescriptions, etc.
        // Uncomment and implement based on your requirements:

        // if (pharmacy.getInventoryItems() != null && !pharmacy.getInventoryItems().isEmpty()) {
        //     throw new InvalidRequestException("Cannot delete pharmacy with existing inventory items");
        // }

        repository.deleteById(id);
    }

    public List<Pharmacy> getAll() {
        return repository.findAll();
    }

    public Pharmacy findByAddress(String address) {
        return repository.findByAddressIgnoreCase(address)
                .orElseThrow(() -> new ResourceNotFoundException("Pharmacy not found at address: " + address));
    }

    public boolean existsAtLocation(String address) {
        return repository.findByAddressIgnoreCase(address).isPresent();
    }
}
