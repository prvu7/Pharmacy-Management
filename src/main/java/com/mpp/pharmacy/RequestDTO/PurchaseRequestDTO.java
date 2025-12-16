package com.mpp.pharmacy.RequestDTO;

import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseRequestDTO {
    @NotNull(message = "Pharmacy ID is required")
    private Long pharmacyId;

    @NotNull(message = "Patient ID is required")
    private Long patientId;

    private Long prescriptionId;

    @NotNull(message = "Purchase date is required")
    @PastOrPresent(message = "Purchase date cannot be in the future")
    private LocalDate purchaseDate;

    @NotNull(message = "Total amount is required")
    @DecimalMin(value = "0.01", message = "Total amount must be greater than zero")
    @DecimalMax(value = "1000000.00", message = "Total amount cannot exceed 1000000")
    @Digits(integer = 7, fraction = 2, message = "Total amount must have at most 2 decimal places")
    private BigDecimal totalAmount;
}