package com.mpp.pharmacy.DTO;

import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrescriptionDTO {
    private Long prescriptionId;
    private Long patientId;
    private Long doctorId;
    private Long treatmentId;
    private LocalDate prescriptionDate;
    private String notes;
    private List<PrescriptionDetailDTO> details;
}