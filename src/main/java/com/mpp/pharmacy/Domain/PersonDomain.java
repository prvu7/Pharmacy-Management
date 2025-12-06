package com.mpp.pharmacy.Domain;

import com.mpp.pharmacy.Entity.Person;
import com.mpp.pharmacy.Exception.DuplicateResourceException;
import com.mpp.pharmacy.Exception.ResourceNotFoundException;
import com.mpp.pharmacy.Repository.PersonRepository;
import com.mpp.pharmacy.RequestDTO.PersonRequestDTO;
import com.mpp.pharmacy.Validators.PersonValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PersonDomain {

    private final PersonRepository repository;
    private final PersonValidator validator;

    public Person create(PersonRequestDTO request) {
        log.debug("Domain: Creating person with email: {}", request.getEmail());

        // Create entity from request
        Person person = Person.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .sex(request.getSex())
                .dateOfBirth(request.getDateOfBirth())
                .phone(request.getPhone())
                .email(request.getEmail())
                .address(request.getAddress())
                .role(request.getRole())
                .build();

        // Validate all fields for creation
        validator.validateForCreation(person);

        // Business rule: Check for duplicate email
        if (repository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already exists: " + request.getEmail());
        }

        // Business rule: Check for duplicate phone
        if (repository.existsByPhone(request.getPhone())) {
            throw new DuplicateResourceException("Phone number already exists: " + request.getPhone());
        }

        log.info("Domain: Person validation passed, saving to database");
        return repository.save(person);
    }

    public Person update(Long id, PersonRequestDTO request) {
        log.debug("Domain: Updating person with id: {}", id);

        // Fetch existing person
        Person existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found: " + id));

        // Update fields
        existing.setFirstName(request.getFirstName());
        existing.setLastName(request.getLastName());
        existing.setSex(request.getSex());
        existing.setDateOfBirth(request.getDateOfBirth());
        existing.setPhone(request.getPhone());
        existing.setEmail(request.getEmail());
        existing.setAddress(request.getAddress());
        existing.setRole(request.getRole());

        // Validate updated entity
        validator.validateForUpdate(existing);

        // Business rule: Check for duplicate email (excluding current person)
        repository.findByEmail(request.getEmail())
                .ifPresent(person -> {
                    if (!person.getPersonId().equals(id)) {
                        throw new DuplicateResourceException("Email already exists: " + request.getEmail());
                    }
                });

        // Business rule: Check for duplicate phone (excluding current person)
        repository.findByPhone(request.getPhone())
                .ifPresent(person -> {
                    if (!person.getPersonId().equals(id)) {
                        throw new DuplicateResourceException("Phone number already exists: " + request.getPhone());
                    }
                });

        log.info("Domain: Person update validation passed, saving to database");
        return repository.save(existing);
    }

    public Person getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found: " + id));
    }

    public void delete(Long id) {
        log.debug("Domain: Deleting person with id: {}", id);

        Person person = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found: " + id));

        // Business rule: Check if person can be deleted
        // Example: Check if person has active prescriptions, appointments, etc.
        // You can add these checks here based on your business requirements

        repository.deleteById(id);
        log.info("Domain: Person deleted successfully");
    }

    public void validateRoleForAction(Long personId, String requiredRole, String action) {
        Person person = getById(personId);
        validator.validateRoleForAction(person, requiredRole, action);
    }

    public void validateMinimumAge(Long personId, int minimumAge, String reason) {
        Person person = getById(personId);
        validator.validateMinimumAge(person, minimumAge, reason);
    }
}
