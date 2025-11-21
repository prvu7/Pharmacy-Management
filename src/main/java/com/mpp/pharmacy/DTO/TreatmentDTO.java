package com.mpp.pharmacy.DTO;

import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TreatmentDTO {
    private Long treatmentId;
    private String treatmentName;
    private String description;
    private Long doctorId; // reference to Person
    private LocalDate startDate;
    private LocalDate endDate;
}