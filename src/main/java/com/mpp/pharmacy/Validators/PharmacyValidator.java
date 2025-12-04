package com.mpp.pharmacy.Validators;

import com.mpp.pharmacy.Entity.Pharmacy;
import com.mpp.pharmacy.Exception.InvalidRequestException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class PharmacyValidator {

    // Business rule constants
    private static final int MIN_NAME_LENGTH = 3;
    private static final int MAX_NAME_LENGTH = 100;
    private static final int MIN_ADDRESS_LENGTH = 10;
    private static final int MAX_ADDRESS_LENGTH = 200;

    // Regex pattern for phone validation
    private static final Pattern PHONE_PATTERN = Pattern.compile("^[+]?[0-9]{10,15}$");

    public void validateForCreation(Pharmacy pharmacy) {
        ValidationResult result = new ValidationResult();

        if (pharmacy == null) {
            throw new InvalidRequestException("Pharmacy object cannot be null");
        }

        // Validate required fields
        validateName(pharmacy.getName(), result, true);
        validateAddress(pharmacy.getAddress(), result, true);
        validatePhone(pharmacy.getPhone(), result, true);

        if (result.hasErrors()) {
            throw new InvalidRequestException(result.getErrorMessage());
        }
    }

    public void validateForUpdate(Pharmacy pharmacy) {
        ValidationResult result = new ValidationResult();

        if (pharmacy == null) {
            throw new InvalidRequestException("Pharmacy object cannot be null");
        }

        if (pharmacy.getPharmacyId() == null) {
            result.addError("Pharmacy ID is required for update operations");
        }

        // Validate only non-null fields
        if (pharmacy.getName() != null) {
            validateName(pharmacy.getName(), result, false);
        }
        if (pharmacy.getAddress() != null) {
            validateAddress(pharmacy.getAddress(), result, false);
        }
        if (pharmacy.getPhone() != null) {
            validatePhone(pharmacy.getPhone(), result, false);
        }

        if (result.hasErrors()) {
            throw new InvalidRequestException(result.getErrorMessage());
        }
    }


    private void validateName(String name, ValidationResult result, boolean required) {
        if (name == null || name.trim().isEmpty()) {
            if (required) {
                result.addError("Pharmacy name is required");
            }
            return;
        }

        String trimmedName = name.trim();
        if (trimmedName.length() < MIN_NAME_LENGTH) {
            result.addError(String.format("Pharmacy name must be at least %d characters long", MIN_NAME_LENGTH));
        }
        if (trimmedName.length() > MAX_NAME_LENGTH) {
            result.addError(String.format("Pharmacy name must not exceed %d characters", MAX_NAME_LENGTH));
        }
        // Allow letters, numbers, spaces, and common punctuation
        if (!trimmedName.matches("^[a-zA-Z0-9\\s.,'&-]+$")) {
            result.addError("Pharmacy name can only contain letters, numbers, spaces, and basic punctuation (.,'-&)");
        }
    }

    private void validateAddress(String address, ValidationResult result, boolean required) {
        if (address == null || address.trim().isEmpty()) {
            if (required) {
                result.addError("Address is required");
            }
            return;
        }

        String trimmedAddress = address.trim();
        if (trimmedAddress.length() < MIN_ADDRESS_LENGTH) {
            result.addError(String.format("Address must be at least %d characters long", MIN_ADDRESS_LENGTH));
        }
        if (trimmedAddress.length() > MAX_ADDRESS_LENGTH) {
            result.addError(String.format("Address must not exceed %d characters", MAX_ADDRESS_LENGTH));
        }
    }

    private void validatePhone(String phone, ValidationResult result, boolean required) {
        if (phone == null || phone.trim().isEmpty()) {
            if (required) {
                result.addError("Phone number is required");
            }
            return;
        }

        // Remove common formatting characters for validation
        String cleanedPhone = phone.replaceAll("[\\s()-]", "");

        if (!PHONE_PATTERN.matcher(cleanedPhone).matches()) {
            result.addError("Invalid phone number format. Must be 10-15 digits, optionally starting with '+'");
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