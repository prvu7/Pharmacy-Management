package com.mpp.pharmacy.Validators;

import com.mpp.pharmacy.Entity.Prescription;
import com.mpp.pharmacy.Exception.InvalidRequestException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class PrescriptionValidator {

    private static final int MAX_NOTES_LENGTH = 1000;

    public void validateForCreation(Prescription prescription) {
        ValidationResult result = new ValidationResult();

        if (prescription == null) {
            throw new InvalidRequestException("Prescription object cannot be null");
        }

        validatePatient(prescription.getPatient(), result, true);
        validateDoctor(prescription.getDoctor(), result, true);
        validatePrescriptionDate(prescription.getPrescriptionDate(), result, true);
        validateNotes(prescription.getNotes(), result, false);

        if (result.hasErrors()) {
            throw new InvalidRequestException(result.getErrorMessage());
        }
    }

    public void validateForUpdate(Prescription prescription) {
        ValidationResult result = new ValidationResult();

        if (prescription == null) {
            throw new InvalidRequestException("Prescription object cannot be null");
        }

        if (prescription.getPrescriptionId() == null) {
            result.addError("Prescription ID is required for update operations");
        }

        if (prescription.getPatient() != null) {
            validatePatient(prescription.getPatient(), result, false);
        }
        if (prescription.getDoctor() != null) {
            validateDoctor(prescription.getDoctor(), result, false);
        }
        if (prescription.getPrescriptionDate() != null) {
            validatePrescriptionDate(prescription.getPrescriptionDate(), result, false);
        }
        if (prescription.getNotes() != null) {
            validateNotes(prescription.getNotes(), result, false);
        }

        if (result.hasErrors()) {
            throw new InvalidRequestException(result.getErrorMessage());
        }
    }

    private void validatePatient(Object patient, ValidationResult result, boolean required) {
        if (patient == null && required) {
            result.addError("Patient is required for prescription");
        }
    }

    private void validateDoctor(Object doctor, ValidationResult result, boolean required) {
        if (doctor == null && required) {
            result.addError("Doctor is required for prescription");
        }
    }

    private void validatePrescriptionDate(LocalDate prescriptionDate, ValidationResult result, boolean required) {
        if (prescriptionDate == null) {
            if (required) {
                result.addError("Prescription date is required");
            }
            return;
        }

        LocalDate today = LocalDate.now();

        if (prescriptionDate.isAfter(today)) {
            result.addError("Prescription date cannot be in the future");
        }

        if (prescriptionDate.isBefore(today.minusYears(5))) {
            result.addError("Prescription date cannot be more than 5 years in the past");
        }
    }

    private void validateNotes(String notes, ValidationResult result, boolean required) {
        if (notes == null || notes.trim().isEmpty()) {
            if (required) {
                result.addError("Notes are required");
            }
            return;
        }

        String trimmedNotes = notes.trim();
        if (trimmedNotes.length() > MAX_NOTES_LENGTH) {
            result.addError(String.format("Notes must not exceed %d characters", MAX_NOTES_LENGTH));
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