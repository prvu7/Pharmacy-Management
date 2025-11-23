package com.mpp.pharmacy.RequestDTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrescriptionDetailRequestDTO {
    private Long prescriptionId;
    private Long drugId;
    private String dosage;
    private Integer durationDays;
    private Integer quantity;
}
