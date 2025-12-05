package com.mpp.pharmacy.Domain;

import com.mpp.pharmacy.Entity.Drug;
import com.mpp.pharmacy.Entity.Prescription;
import com.mpp.pharmacy.Entity.PrescriptionDetailId;
import com.mpp.pharmacy.Entity.Prescription_Detail;
import com.mpp.pharmacy.Exception.DuplicateResourceException;
import com.mpp.pharmacy.Exception.InvalidRequestException;
import com.mpp.pharmacy.Exception.ResourceNotFoundException;
import com.mpp.pharmacy.Repository.DrugRepository;
import com.mpp.pharmacy.Repository.PrescriptionDetailRepository;
import com.mpp.pharmacy.Repository.PrescriptionRepository;
import com.mpp.pharmacy.RequestDTO.PrescriptionDetailRequestDTO;
import com.mpp.pharmacy.Validators.PrescriptionDetailValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PrescriptionDetailDomain {

    private final PrescriptionDetailRepository repository;
    private final PrescriptionRepository prescriptionRepository;
    private final DrugRepository drugRepository;
    private final PrescriptionDetailValidator validator;

    public Prescription_Detail create(PrescriptionDetailRequestDTO request) {
        log.debug("Domain: Creating prescription detail");

        Prescription prescription = validateAndFetchPrescription(request.getPrescriptionId());
        Drug drug = validateAndFetchDrug(request.getDrugId());

        PrescriptionDetailId id = new PrescriptionDetailId(request.getPrescriptionId(), request.getDrugId());
        if (repository.existsById(id)) {
            throw new DuplicateResourceException(
                    String.format("Prescription detail already exists for prescription %d and drug %d",
                            request.getPrescriptionId(), request.getDrugId())
            );
        }

        Prescription_Detail detail = Prescription_Detail.builder()
                .prescription(prescription)
                .drug(drug)
                .dosage(request.getDosage())
                .durationDays(request.getDurationDays())
                .quantity(request.getQuantity())
                .build();

        validator.validateForCreation(detail);

        log.info("Domain: Prescription detail validation passed, saving to database");
        return repository.save(detail);
    }

    public Prescription_Detail update(Long prescriptionId, Long drugId, PrescriptionDetailRequestDTO request) {
        log.debug("Domain: Updating prescription detail with prescriptionId: {} and drugId: {}", prescriptionId, drugId);

        PrescriptionDetailId id = new PrescriptionDetailId(prescriptionId, drugId);
        Prescription_Detail existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Prescription detail not found"));

        existing.setDosage(request.getDosage());
        existing.setDurationDays(request.getDurationDays());
        existing.setQuantity(request.getQuantity());

        validator.validateForUpdate(existing);

        log.info("Domain: Prescription detail update validation passed, saving to database");
        return repository.save(existing);
    }

    public Prescription_Detail get(Long prescriptionId, Long drugId) {
        PrescriptionDetailId id = new PrescriptionDetailId(prescriptionId, drugId);
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Prescription detail not found"));
    }

    public void delete(Long prescriptionId, Long drugId) {
        log.debug("Domain: Deleting prescription detail with prescriptionId: {} and drugId: {}", prescriptionId, drugId);

        PrescriptionDetailId id = new PrescriptionDetailId(prescriptionId, drugId);
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Prescription detail not found");
        }

        repository.deleteById(id);
        log.info("Domain: Prescription detail deleted successfully");
    }

    private Prescription validateAndFetchPrescription(Long prescriptionId) {
        if (prescriptionId == null) {
            throw new InvalidRequestException("Prescription ID is required");
        }

        return prescriptionRepository.findById(prescriptionId)
                .orElseThrow(() -> new ResourceNotFoundException("Prescription not found: " + prescriptionId));
    }

    private Drug validateAndFetchDrug(Long drugId) {
        if (drugId == null) {
            throw new InvalidRequestException("Drug ID is required");
        }

        return drugRepository.findById(drugId)
                .orElseThrow(() -> new ResourceNotFoundException("Drug not found: " + drugId));
    }
}