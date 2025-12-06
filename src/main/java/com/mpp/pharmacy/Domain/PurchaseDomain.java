package com.mpp.pharmacy.Domain;

import com.mpp.pharmacy.Entity.Person;
import com.mpp.pharmacy.Entity.Pharmacy;
import com.mpp.pharmacy.Entity.Prescription;
import com.mpp.pharmacy.Entity.Purchase;
import com.mpp.pharmacy.Enum.Role;
import com.mpp.pharmacy.Exception.InvalidRequestException;
import com.mpp.pharmacy.Exception.ResourceNotFoundException;
import com.mpp.pharmacy.Repository.PersonRepository;
import com.mpp.pharmacy.Repository.PharmacyRepository;
import com.mpp.pharmacy.Repository.PrescriptionRepository;
import com.mpp.pharmacy.Repository.PurchaseRepository;
import com.mpp.pharmacy.RequestDTO.PurchaseRequestDTO;
import com.mpp.pharmacy.Validators.PurchaseValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PurchaseDomain {

    private final PurchaseRepository purchaseRepository;
    private final PharmacyRepository pharmacyRepository;
    private final PersonRepository personRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final PurchaseValidator validator;

    public Purchase create(PurchaseRequestDTO request) {
        log.debug("Domain: Creating purchase");

        Pharmacy pharmacy = validateAndFetchPharmacy(request.getPharmacyId());
        Person patient = validateAndFetchPatient(request.getPatientId());
        Prescription prescription = validateAndFetchPrescription(request.getPrescriptionId());

        if (prescription != null) {
            validatePrescriptionOwnership(prescription, patient);
        }

        Purchase purchase = Purchase.builder()
                .pharmacy(pharmacy)
                .patient(patient)
                .prescription(prescription)
                .purchaseDate(request.getPurchaseDate())
                .totalAmount(request.getTotalAmount())
                .build();

        validator.validateForCreation(purchase);

        log.info("Domain: Purchase validation passed, saving to database");
        return purchaseRepository.save(purchase);
    }

    public Purchase update(Long id, PurchaseRequestDTO request) {
        log.debug("Domain: Updating purchase with id: {}", id);

        Purchase existing = purchaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase not found: " + id));

        Pharmacy pharmacy = validateAndFetchPharmacy(request.getPharmacyId());
        Person patient = validateAndFetchPatient(request.getPatientId());
        Prescription prescription = validateAndFetchPrescription(request.getPrescriptionId());

        if (prescription != null) {
            validatePrescriptionOwnership(prescription, patient);
        }

        existing.setPharmacy(pharmacy);
        existing.setPatient(patient);
        existing.setPrescription(prescription);
        existing.setPurchaseDate(request.getPurchaseDate());
        existing.setTotalAmount(request.getTotalAmount());

        validator.validateForUpdate(existing);

        log.info("Domain: Purchase update validation passed, saving to database");
        return purchaseRepository.save(existing);
    }

    public Purchase getById(Long id) {
        return purchaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase not found: " + id));
    }

    public void delete(Long id) {
        log.debug("Domain: Deleting purchase with id: {}", id);

        Purchase purchase = purchaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase not found: " + id));

        purchaseRepository.deleteById(id);
        log.info("Domain: Purchase deleted successfully");
    }

    private Pharmacy validateAndFetchPharmacy(Long pharmacyId) {
        if (pharmacyId == null) {
            throw new InvalidRequestException("Pharmacy ID is required");
        }

        return pharmacyRepository.findById(pharmacyId)
                .orElseThrow(() -> new ResourceNotFoundException("Pharmacy not found: " + pharmacyId));
    }

    private Person validateAndFetchPatient(Long patientId) {
        if (patientId == null) {
            throw new InvalidRequestException("Patient ID is required");
        }

        Person patient = personRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found: " + patientId));

        if (patient.getRole() != Role.patient) {
            throw new InvalidRequestException("Person with ID " + patientId + " is not a patient. Role: " + patient.getRole());
        }

        return patient;
    }

    private Prescription validateAndFetchPrescription(Long prescriptionId) {
        if (prescriptionId == null) {
            return null;
        }

        return prescriptionRepository.findById(prescriptionId)
                .orElseThrow(() -> new ResourceNotFoundException("Prescription not found: " + prescriptionId));
    }

    private void validatePrescriptionOwnership(Prescription prescription, Person patient) {
        if (prescription.getPatient() != null &&
                !prescription.getPatient().getPersonId().equals(patient.getPersonId())) {
            throw new InvalidRequestException(
                    "Prescription does not belong to the specified patient"
            );
        }
    }
}