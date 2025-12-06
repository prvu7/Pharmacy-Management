package com.mpp.pharmacy.Validators;

import com.mpp.pharmacy.Entity.Purchase;
import com.mpp.pharmacy.Exception.InvalidRequestException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class PurchaseValidator {

    private static final BigDecimal MIN_AMOUNT = BigDecimal.ZERO;
    private static final BigDecimal MAX_AMOUNT = new BigDecimal("1000000");

    public void validateForCreation(Purchase purchase) {
        ValidationResult result = new ValidationResult();

        if (purchase == null) {
            throw new InvalidRequestException("Purchase object cannot be null");
        }

        validatePharmacy(purchase.getPharmacy(), result, true);
        validatePatient(purchase.getPatient(), result, true);
        validatePurchaseDate(purchase.getPurchaseDate(), result, true);
        validateTotalAmount(purchase.getTotalAmount(), result, true);

        if (result.hasErrors()) {
            throw new InvalidRequestException(result.getErrorMessage());
        }
    }

    public void validateForUpdate(Purchase purchase) {
        ValidationResult result = new ValidationResult();

        if (purchase == null) {
            throw new InvalidRequestException("Purchase object cannot be null");
        }

        if (purchase.getPurchaseId() == null) {
            result.addError("Purchase ID is required for update operations");
        }

        if (purchase.getPharmacy() != null) {
            validatePharmacy(purchase.getPharmacy(), result, false);
        }
        if (purchase.getPatient() != null) {
            validatePatient(purchase.getPatient(), result, false);
        }
        if (purchase.getPurchaseDate() != null) {
            validatePurchaseDate(purchase.getPurchaseDate(), result, false);
        }
        if (purchase.getTotalAmount() != null) {
            validateTotalAmount(purchase.getTotalAmount(), result, false);
        }

        if (result.hasErrors()) {
            throw new InvalidRequestException(result.getErrorMessage());
        }
    }

    private void validatePharmacy(Object pharmacy, ValidationResult result, boolean required) {
        if (pharmacy == null && required) {
            result.addError("Pharmacy is required for purchase");
        }
    }

    private void validatePatient(Object patient, ValidationResult result, boolean required) {
        if (patient == null && required) {
            result.addError("Patient is required for purchase");
        }
    }

    private void validatePurchaseDate(LocalDate purchaseDate, ValidationResult result, boolean required) {
        if (purchaseDate == null) {
            if (required) {
                result.addError("Purchase date is required");
            }
            return;
        }

        LocalDate today = LocalDate.now();
        if (purchaseDate.isAfter(today)) {
            result.addError("Purchase date cannot be in the future");
        }

        if (purchaseDate.isBefore(today.minusYears(10))) {
            result.addError("Purchase date cannot be more than 10 years in the past");
        }
    }

    private void validateTotalAmount(BigDecimal totalAmount, ValidationResult result, boolean required) {
        if (totalAmount == null) {
            if (required) {
                result.addError("Total amount is required");
            }
            return;
        }

        if (totalAmount.compareTo(MIN_AMOUNT) <= 0) {
            result.addError("Total amount must be greater than zero");
        }

        if (totalAmount.compareTo(MAX_AMOUNT) > 0) {
            result.addError(String.format("Total amount cannot exceed %s", MAX_AMOUNT));
        }

        if (totalAmount.scale() > 2) {
            result.addError("Total amount cannot have more than 2 decimal places");
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