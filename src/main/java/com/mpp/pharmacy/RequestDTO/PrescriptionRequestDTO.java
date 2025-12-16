package com.mpp.pharmacy.RequestDTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrescriptionRequestDTO {

    @NotNull(message = "Patient ID is required")
    private Long patientId;

    @NotNull(message = "Doctor ID is required")
    private Long doctorId;

    private Long treatmentId;

    @NotNull(message = "Prescription date is required")
    @PastOrPresent(message = "Prescription date cannot be in the future")
    private LocalDate prescriptionDate;

    @Size(max = 1000, message = "Notes must not exceed 1000 characters")
    private String notes;
}