package com.mpp.pharmacy.RequestDTO;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TreatmentRequestDTO {
    private String treatmentName;
    private String description;
    private Long doctorId;
    private LocalDate startDate;
    private LocalDate endDate;
}