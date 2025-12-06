package com.mpp.pharmacy.Validators;

import com.mpp.pharmacy.Entity.Purchase_Detail;
import com.mpp.pharmacy.Exception.InvalidRequestException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class PurchaseDetailValidator {

    private static final int MIN_QUANTITY = 1;
    private static final int MAX_QUANTITY = 10000;
    private static final BigDecimal MIN_UNIT_PRICE = BigDecimal.ZERO;
    private static final BigDecimal MAX_UNIT_PRICE = new BigDecimal("100000");

    public void validateForCreation(Purchase_Detail detail) {
        ValidationResult result = new ValidationResult();

        if (detail == null) {
            throw new InvalidRequestException("Purchase detail object cannot be null");
        }

        validatePurchase(detail.getPurchase(), result, true);
        validateDrug(detail.getDrug(), result, true);
        validateQuantity(detail.getQuantity(), result, true);
        validateUnitPrice(detail.getUnitPrice(), result, true);

        if (result.hasErrors()) {
            throw new InvalidRequestException(result.getErrorMessage());
        }
    }

    public void validateForUpdate(Purchase_Detail detail) {
        ValidationResult result = new ValidationResult();

        if (detail == null) {
            throw new InvalidRequestException("Purchase detail object cannot be null");
        }

        if (detail.getPurchase() == null || detail.getDrug() == null) {
            result.addError("Purchase and Drug are required for update operations");
        }

        if (detail.getQuantity() != null) {
            validateQuantity(detail.getQuantity(), result, false);
        }
        if (detail.getUnitPrice() != null) {
            validateUnitPrice(detail.getUnitPrice(), result, false);
        }

        if (result.hasErrors()) {
            throw new InvalidRequestException(result.getErrorMessage());
        }
    }

    private void validatePurchase(Object purchase, ValidationResult result, boolean required) {
        if (purchase == null && required) {
            result.addError("Purchase is required for purchase detail");
        }
    }

    private void validateDrug(Object drug, ValidationResult result, boolean required) {
        if (drug == null && required) {
            result.addError("Drug is required for purchase detail");
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

    private void validateUnitPrice(BigDecimal unitPrice, ValidationResult result, boolean required) {
        if (unitPrice == null) {
            if (required) {
                result.addError("Unit price is required");
            }
            return;
        }

        if (unitPrice.compareTo(MIN_UNIT_PRICE) <= 0) {
            result.addError("Unit price must be greater than zero");
        }

        if (unitPrice.compareTo(MAX_UNIT_PRICE) > 0) {
            result.addError(String.format("Unit price cannot exceed %s", MAX_UNIT_PRICE));
        }

        if (unitPrice.scale() > 2) {
            result.addError("Unit price cannot have more than 2 decimal places");
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