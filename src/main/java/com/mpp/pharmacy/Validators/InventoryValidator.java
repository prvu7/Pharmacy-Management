package com.mpp.pharmacy.Validators;

import com.mpp.pharmacy.Entity.Inventory;
import com.mpp.pharmacy.Exception.InvalidRequestException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class InventoryValidator {

    private static final int MIN_QUANTITY = 0;
    private static final int MAX_QUANTITY = 1000000;
    private static final int MIN_EXPIRY_DAYS = 0;
    private static final int MAX_EXPIRY_YEARS = 10;

    public void validateForCreation(Inventory inventory) {
        ValidationResult result = new ValidationResult();

        if (inventory == null) {
            throw new InvalidRequestException("Inventory object cannot be null");
        }

        validatePharmacy(inventory.getPharmacy(), result, true);
        validateDrug(inventory.getDrug(), result, true);
        validateQuantity(inventory.getQuantityInStock(), result, true);
        validateExpiryDate(inventory.getExpiryDate(), result, true);

        if (result.hasErrors()) {
            throw new InvalidRequestException(result.getErrorMessage());
        }
    }

    public void validateForUpdate(Inventory inventory) {
        ValidationResult result = new ValidationResult();

        if (inventory == null) {
            throw new InvalidRequestException("Inventory object cannot be null");
        }

        if (inventory.getInventoryId() == null) {
            result.addError("Inventory ID is required for update operations");
        }

        if (inventory.getPharmacy() != null) {
            validatePharmacy(inventory.getPharmacy(), result, false);
        }
        if (inventory.getDrug() != null) {
            validateDrug(inventory.getDrug(), result, false);
        }
        if (inventory.getQuantityInStock() != null) {
            validateQuantity(inventory.getQuantityInStock(), result, false);
        }
        if (inventory.getExpiryDate() != null) {
            validateExpiryDate(inventory.getExpiryDate(), result, false);
        }

        if (result.hasErrors()) {
            throw new InvalidRequestException(result.getErrorMessage());
        }
    }

    private void validatePharmacy(Object pharmacy, ValidationResult result, boolean required) {
        if (pharmacy == null && required) {
            result.addError("Pharmacy is required for inventory");
        }
    }

    private void validateDrug(Object drug, ValidationResult result, boolean required) {
        if (drug == null && required) {
            result.addError("Drug is required for inventory");
        }
    }

    private void validateQuantity(Integer quantity, ValidationResult result, boolean required) {
        if (quantity == null) {
            if (required) {
                result.addError("Quantity in stock is required");
            }
            return;
        }

        if (quantity < MIN_QUANTITY) {
            result.addError("Quantity in stock cannot be negative");
        }

        if (quantity > MAX_QUANTITY) {
            result.addError(String.format("Quantity in stock cannot exceed %d", MAX_QUANTITY));
        }
    }

    private void validateExpiryDate(LocalDate expiryDate, ValidationResult result, boolean required) {
        if (expiryDate == null) {
            if (required) {
                result.addError("Expiry date is required");
            }
            return;
        }

        LocalDate today = LocalDate.now();

        if (expiryDate.isBefore(today.minusDays(MIN_EXPIRY_DAYS))) {
            result.addError("Expiry date cannot be in the past");
        }

        if (expiryDate.isAfter(today.plusYears(MAX_EXPIRY_YEARS))) {
            result.addError(String.format("Expiry date cannot be more than %d years in the future", MAX_EXPIRY_YEARS));
        }
    }

    public void validateStockAvailability(Integer currentStock, Integer requestedQuantity) {
        if (currentStock == null || requestedQuantity == null) {
            throw new InvalidRequestException("Stock quantities cannot be null");
        }

        if (requestedQuantity > currentStock) {
            throw new InvalidRequestException(
                    String.format("Insufficient stock. Available: %d, Requested: %d", currentStock, requestedQuantity)
            );
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