package com.mpp.pharmacy.RequestDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DrugRequestDTO {

    @NotBlank(message = "Drug name is required")
    @Size(min = 2, max = 100, message = "Drug name must be between 2 and 100 characters")
    @Pattern(regexp = "^[a-zA-Z0-9\\s\\-\\.]+$",
            message = "Drug name can only contain letters, numbers, spaces, hyphens, and periods")
    private String drugName;

    @Size(max = 100, message = "Generic name must not exceed 100 characters")
    @Pattern(regexp = "^[a-zA-Z0-9\\s\\-\\.]+$",
            message = "Generic name can only contain letters, numbers, spaces, hyphens, and periods")
    private String genericName;

    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;

    @NotBlank(message = "Dosage form is required")
    @Pattern(regexp = "^(tablet|capsule|syrup|injection|cream|ointment|drops|spray|powder|gel|patch|inhaler|solution)$",
            flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "Invalid dosage form. Valid forms: tablet, capsule, syrup, injection, cream, ointment, drops, spray, powder, gel, patch, inhaler, solution")
    private String dosageForm;

    @NotBlank(message = "Manufacturer is required")
    @Size(min = 2, max = 100, message = "Manufacturer name must be between 2 and 100 characters")
    private String manufacturer;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01", message = "Price must be at least 0.01")
    @DecimalMax(value = "999999.99", message = "Price must not exceed 999999.99")
    @Digits(integer = 6, fraction = 2, message = "Price can have at most 2 decimal places")
    private BigDecimal price;
}