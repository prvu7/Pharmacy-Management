package com.mpp.pharmacy.RequestDTO;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryRequestDTO {
    @NotNull(message = "Pharmacy ID is required")
    private Long pharmacyId;

    @NotNull(message = "Drug ID is required")
    private Long drugId;

    @NotNull(message = "Quantity in stock is required")
    @Min(value = 0, message = "Quantity cannot be negative")
    @Max(value = 1000000, message = "Quantity cannot exceed 1000000")
    private Integer quantityInStock;

    @NotNull(message = "Expiry date is required")
    @Future(message = "Expiry date must be in the future")
    private LocalDate expiryDate;
}