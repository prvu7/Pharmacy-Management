package com.mpp.pharmacy.Validators;

import com.mpp.pharmacy.Entity.Drug;
import com.mpp.pharmacy.Exception.InvalidRequestException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class DrugValidator {

    // Valid dosage forms based on common pharmaceutical standards
    private static final List<String> VALID_DOSAGE_FORMS = Arrays.asList(
            "tablet", "capsule", "syrup", "injection", "cream", "ointment",
            "drops", "spray", "powder", "gel", "patch", "inhaler", "solution"
    );

    // Business rule constants
    private static final int MIN_NAME_LENGTH = 2;
    private static final int MAX_NAME_LENGTH = 100;
    private static final int MAX_DESCRIPTION_LENGTH = 1000;
    private static final BigDecimal MIN_PRICE = new BigDecimal("0.01");
    private static final BigDecimal MAX_PRICE = new BigDecimal("999999.99");

    /**
      Validates a Drug entity for creation (all required fields must be present)
     */

    public void validateForCreation(Drug drug) {
        ValidationResult result = new ValidationResult();

        if (drug == null) {
            throw new InvalidRequestException("Drug object cannot be null");
        }

        // Validate required fields
        validateDrugName(drug.getDrugName(), result, true);
        validateGenericName(drug.getGenericName(), result, false);
        validateDescription(drug.getDescription(), result);
        validateDosageForm(drug.getDosageForm(), result, true);
        validateManufacturer(drug.getManufacturer(), result, true);
        validatePrice(drug.getPrice(), result, true);

        if (result.hasErrors()) {
            throw new InvalidRequestException(result.getErrorMessage());
        }
    }

    /**
     Validates a Drug entity for update (only provided fields are validated)
     */

    public void validateForUpdate(Drug drug) {
        ValidationResult result = new ValidationResult();

        if (drug == null) {
            throw new InvalidRequestException("Drug object cannot be null");
        }

        if (drug.getDrugId() == null) {
            result.addError("Drug ID is required for update operations");
        }

        // Validate only non-null fields (partial update support)
        if (drug.getDrugName() != null) {
            validateDrugName(drug.getDrugName(), result, true);
        }
        if (drug.getGenericName() != null) {
            validateGenericName(drug.getGenericName(), result, false);
        }
        if (drug.getDescription() != null) {
            validateDescription(drug.getDescription(), result);
        }
        if (drug.getDosageForm() != null) {
            validateDosageForm(drug.getDosageForm(), result, false);
        }
        if (drug.getManufacturer() != null) {
            validateManufacturer(drug.getManufacturer(), result, false);
        }
        if (drug.getPrice() != null) {
            validatePrice(drug.getPrice(), result, false);
        }

        if (result.hasErrors()) {
            throw new InvalidRequestException(result.getErrorMessage());
        }
    }

    // Individual field validators

    private void validateDrugName(String drugName, ValidationResult result, boolean required) {
        if (drugName == null || drugName.trim().isEmpty()) {
            if (required) {
                result.addError("Drug name is required");
            }
            return;
        }

        String trimmedName = drugName.trim();
        if (trimmedName.length() < MIN_NAME_LENGTH) {
            result.addError(String.format("Drug name must be at least %d characters long", MIN_NAME_LENGTH));
        }
        if (trimmedName.length() > MAX_NAME_LENGTH) {
            result.addError(String.format("Drug name must not exceed %d characters", MAX_NAME_LENGTH));
        }
        if (!trimmedName.matches("^[a-zA-Z0-9\\s\\-\\.]+$")) {
            result.addError("Drug name can only contain letters, numbers, spaces, hyphens, and periods");
        }
    }

    private void validateGenericName(String genericName, ValidationResult result, boolean required) {
        if (genericName == null || genericName.trim().isEmpty()) {
            if (required) {
                result.addError("Generic name is required");
            }
            return;
        }

        String trimmedName = genericName.trim();
        if (trimmedName.length() > MAX_NAME_LENGTH) {
            result.addError(String.format("Generic name must not exceed %d characters", MAX_NAME_LENGTH));
        }
        if (!trimmedName.matches("^[a-zA-Z0-9\\s\\-\\.]+$")) {
            result.addError("Generic name can only contain letters, numbers, spaces, hyphens, and periods");
        }
    }

    private void validateDescription(String description, ValidationResult result) {
        if (description != null && description.length() > MAX_DESCRIPTION_LENGTH) {
            result.addError(String.format("Description must not exceed %d characters", MAX_DESCRIPTION_LENGTH));
        }
    }

    private void validateDosageForm(String dosageForm, ValidationResult result, boolean required) {
        if (dosageForm == null || dosageForm.trim().isEmpty()) {
            if (required) {
                result.addError("Dosage form is required");
            }
            return;
        }

        String normalizedForm = dosageForm.trim().toLowerCase();
        if (!VALID_DOSAGE_FORMS.contains(normalizedForm)) {
            result.addError(String.format("Invalid dosage form '%s'. Valid forms: %s",
                    dosageForm, String.join(", ", VALID_DOSAGE_FORMS)));
        }
    }

    private void validateManufacturer(String manufacturer, ValidationResult result, boolean required) {
        if (manufacturer == null || manufacturer.trim().isEmpty()) {
            if (required) {
                result.addError("Manufacturer is required");
            }
            return;
        }

        String trimmedManufacturer = manufacturer.trim();
        if (trimmedManufacturer.length() < MIN_NAME_LENGTH) {
            result.addError(String.format("Manufacturer name must be at least %d characters long", MIN_NAME_LENGTH));
        }
        if (trimmedManufacturer.length() > MAX_NAME_LENGTH) {
            result.addError(String.format("Manufacturer name must not exceed %d characters", MAX_NAME_LENGTH));
        }
    }

    private void validatePrice(BigDecimal price, ValidationResult result, boolean required) {
        if (price == null) {
            if (required) {
                result.addError("Price is required");
            }
            return;
        }

        if (price.compareTo(MIN_PRICE) < 0) {
            result.addError(String.format("Price must be at least %s", MIN_PRICE));
        }
        if (price.compareTo(MAX_PRICE) > 0) {
            result.addError(String.format("Price must not exceed %s", MAX_PRICE));
        }
        if (price.scale() > 2) {
            result.addError("Price can have at most 2 decimal places");
        }
    }

    /**
      Internal class to collect validation errors
     */
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
