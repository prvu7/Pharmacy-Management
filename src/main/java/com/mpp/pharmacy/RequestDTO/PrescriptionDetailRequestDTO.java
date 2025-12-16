package com.mpp.pharmacy.RequestDTO;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrescriptionDetailRequestDTO {
    @NotNull(message = "Prescription ID is required")
    private Long prescriptionId;

    @NotNull(message = "Drug ID is required")
    private Long drugId;

    @NotBlank(message = "Dosage is required")
    @Size(min = 2, max = 100, message = "Dosage must be between 2 and 100 characters")
    private String dosage;

    @NotNull(message = "Duration days is required")
    @Min(value = 1, message = "Duration must be at least 1 day")
    @Max(value = 365, message = "Duration cannot exceed 365 days")
    private Integer durationDays;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    @Max(value = 10000, message = "Quantity cannot exceed 10000")
    private Integer quantity;
}
