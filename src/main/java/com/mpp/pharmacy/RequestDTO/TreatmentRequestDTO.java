package com.mpp.pharmacy.RequestDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TreatmentRequestDTO {

    @NotBlank(message = "Treatment name is required")
    @Size(min = 3, max = 100, message = "Treatment name must be between 3 and 100 characters")
    private String treatmentName;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    @NotNull(message = "Doctor ID is required")
    private Long doctorId;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    private LocalDate endDate;
}