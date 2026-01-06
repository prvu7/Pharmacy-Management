package com.mpp.pharmacy.RequestDTO;


import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseDetailRequestDTO {
    @NotNull(message = "Purchase ID is required")
    private Long purchaseId;

    @NotNull(message = "Drug ID is required")
    private Long drugId;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    @Max(value = 10000, message = "Quantity cannot exceed 10000")
    private Integer quantity;

    @NotNull(message = "Unit price is required")
    @DecimalMin(value = "0.01", message = "Unit price must be greater than zero")
    @DecimalMax(value = "100000.00", message = "Unit price cannot exceed 100000")
    @Digits(integer = 6, fraction = 2, message = "Unit price must have at most 2 decimal places")
    private BigDecimal unitPrice;
}
