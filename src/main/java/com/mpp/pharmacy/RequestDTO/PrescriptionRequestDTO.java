package com.mpp.pharmacy.RequestDTO;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrescriptionRequestDTO {

    private Long patientId;
    private Long doctorId;
    private Long treatmentId;
    private LocalDate prescriptionDate;
    private String notes;
}