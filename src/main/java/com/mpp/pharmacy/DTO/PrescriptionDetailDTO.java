package com.mpp.pharmacy.DTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrescriptionDetailDTO {
    private Long prescriptionId;
    private Long drugId;
    private String dosage;
    private Integer durationDays;
    private Integer quantity;
}
