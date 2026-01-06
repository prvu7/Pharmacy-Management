package com.mpp.pharmacy.Validators;

import com.mpp.pharmacy.Entity.Prescription_Detail;
import com.mpp.pharmacy.Exception.InvalidRequestException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PrescriptionDetailValidator {

    private static final int MIN_DOSAGE_LENGTH = 2;
    private static final int MAX_DOSAGE_LENGTH = 100;
    private static final int MIN_DURATION_DAYS = 1;
    private static final int MAX_DURATION_DAYS = 365;
    private static final int MIN_QUANTITY = 1;
    private static final int MAX_QUANTITY = 10000;

    public void validateForCreation(Prescription_Detail detail) {
        ValidationResult result = new ValidationResult();

        if (detail == null) {
            throw new InvalidRequestException("Prescription detail object cannot be null");
        }

        validatePrescription(detail.getPrescription(), result, true);
        validateDrug(detail.getDrug(), result, true);
        validateDosage(detail.getDosage(), result, true);
        validateDurationDays(detail.getDurationDays(), result, true);
        validateQuantity(detail.getQuantity(), result, true);

        if (result.hasErrors()) {
            throw new InvalidRequestException(result.getErrorMessage());
        }
    }

    public void validateForUpdate(Prescription_Detail detail) {
        ValidationResult result = new ValidationResult();

        if (detail == null) {
            throw new InvalidRequestException("Prescription detail object cannot be null");
        }

        if (detail.getPrescription() != null) {
            validatePrescription(detail.getPrescription(), result, false);
        }
        if (detail.getDrug() != null) {
            validateDrug(detail.getDrug(), result, false);
        }
        if (detail.getDosage() != null) {
            validateDosage(detail.getDosage(), result, false);
        }
        if (detail.getDurationDays() != null) {
            validateDurationDays(detail.getDurationDays(), result, false);
        }
        if (detail.getQuantity() != null) {
            validateQuantity(detail.getQuantity(), result, false);
        }

        if (result.hasErrors()) {
            throw new InvalidRequestException(result.getErrorMessage());
        }
    }

    private void validatePrescription(Object prescription, ValidationResult result, boolean required) {
        if (prescription == null && required) {
            result.addError("Prescription is required for prescription detail");
        }
    }

    private void validateDrug(Object drug, ValidationResult result, boolean required) {
        if (drug == null && required) {
            result.addError("Drug is required for prescription detail");
        }
    }

    private void validateDosage(String dosage, ValidationResult result, boolean required) {
        if (dosage == null || dosage.trim().isEmpty()) {
            if (required) {
                result.addError("Dosage is required");
            }
            return;
        }

        String trimmedDosage = dosage.trim();
        if (trimmedDosage.length() < MIN_DOSAGE_LENGTH) {
            result.addError(String.format("Dosage must be at least %d characters long", MIN_DOSAGE_LENGTH));
        }
        if (trimmedDosage.length() > MAX_DOSAGE_LENGTH) {
            result.addError(String.format("Dosage must not exceed %d characters", MAX_DOSAGE_LENGTH));
        }
    }

    private void validateDurationDays(Integer durationDays, ValidationResult result, boolean required) {
        if (durationDays == null) {
            if (required) {
                result.addError("Duration days is required");
            }
            return;
        }

        if (durationDays < MIN_DURATION_DAYS) {
            result.addError(String.format("Duration must be at least %d day", MIN_DURATION_DAYS));
        }
        if (durationDays > MAX_DURATION_DAYS) {
            result.addError(String.format("Duration cannot exceed %d days", MAX_DURATION_DAYS));
        }
    }

    private void validateQuantity(Integer quantity, ValidationResult result, boolean required) {
        if (quantity == null) {
            if (required) {
                result.addError("Quantity is required");
            }
            return;
        }

        if (quantity < MIN_QUANTITY) {
            result.addError(String.format("Quantity must be at least %d", MIN_QUANTITY));
        }
        if (quantity > MAX_QUANTITY) {
            result.addError(String.format("Quantity cannot exceed %d", MAX_QUANTITY));
        }
    }

    private static class ValidationResult {
        private final List<String> errors = new ArrayList<>();

        public void addError(String error) {
            errors.add(error);
        }

        public boolean hasErrors() {
            return !errors.isEmpty();
        }

        public String getErrorMessage() {
            return String.join("; ", errors);
        }

        public List<String> getErrors() {
            return new ArrayList<>(errors);
        }
    }
}