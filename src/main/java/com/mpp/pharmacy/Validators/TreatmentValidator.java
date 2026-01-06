package com.mpp.pharmacy.Validators;

import com.mpp.pharmacy.Entity.Treatment;
import com.mpp.pharmacy.Exception.InvalidRequestException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class TreatmentValidator {

    private static final int MIN_NAME_LENGTH = 3;
    private static final int MAX_NAME_LENGTH = 100;
    private static final int MAX_DESCRIPTION_LENGTH = 500;

    public void validateForCreation(Treatment treatment) {
        ValidationResult result = new ValidationResult();

        if (treatment == null) {
            throw new InvalidRequestException("Treatment object cannot be null");
        }

        validateTreatmentName(treatment.getTreatmentName(), result, true);
        validateDescription(treatment.getDescription(), result, false);
        validateDates(treatment.getStartDate(), treatment.getEndDate(), result, true);
        validateDoctor(treatment.getDoctor(), result, true);

        if (result.hasErrors()) {
            throw new InvalidRequestException(result.getErrorMessage());
        }
    }

    public void validateForUpdate(Treatment treatment) {
        ValidationResult result = new ValidationResult();

        if (treatment == null) {
            throw new InvalidRequestException("Treatment object cannot be null");
        }

        if (treatment.getTreatmentId() == null) {
            result.addError("Treatment ID is required for update operations");
        }

        if (treatment.getTreatmentName() != null) {
            validateTreatmentName(treatment.getTreatmentName(), result, false);
        }
        if (treatment.getDescription() != null) {
            validateDescription(treatment.getDescription(), result, false);
        }
        if (treatment.getStartDate() != null || treatment.getEndDate() != null) {
            validateDates(treatment.getStartDate(), treatment.getEndDate(), result, false);
        }
        if (treatment.getDoctor() != null) {
            validateDoctor(treatment.getDoctor(), result, false);
        }

        if (result.hasErrors()) {
            throw new InvalidRequestException(result.getErrorMessage());
        }
    }

    private void validateTreatmentName(String name, ValidationResult result, boolean required) {
        if (name == null || name.trim().isEmpty()) {
            if (required) {
                result.addError("Treatment name is required");
            }
            return;
        }

        String trimmedName = name.trim();
        if (trimmedName.length() < MIN_NAME_LENGTH) {
            result.addError(String.format("Treatment name must be at least %d characters long", MIN_NAME_LENGTH));
        }
        if (trimmedName.length() > MAX_NAME_LENGTH) {
            result.addError(String.format("Treatment name must not exceed %d characters", MAX_NAME_LENGTH));
        }
    }

    private void validateDescription(String description, ValidationResult result, boolean required) {
        if (description == null || description.trim().isEmpty()) {
            if (required) {
                result.addError("Description is required");
            }
            return;
        }

        String trimmedDescription = description.trim();
        if (trimmedDescription.length() > MAX_DESCRIPTION_LENGTH) {
            result.addError(String.format("Description must not exceed %d characters", MAX_DESCRIPTION_LENGTH));
        }
    }

    private void validateDates(LocalDate startDate, LocalDate endDate, ValidationResult result, boolean required) {
        if (startDate == null) {
            if (required) {
                result.addError("Start date is required");
            }
            return;
        }

        if (endDate != null && endDate.isBefore(startDate)) {
            result.addError("End date cannot be before start date");
        }

        if (startDate.isAfter(LocalDate.now().plusYears(1))) {
            result.addError("Start date cannot be more than 1 year in the future");
        }
    }

    private void validateDoctor(Object doctor, ValidationResult result, boolean required) {
        if (doctor == null && required) {
            result.addError("Doctor is required for treatment");
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