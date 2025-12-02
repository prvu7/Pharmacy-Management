package com.mpp.pharmacy.RequestDTO;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseRequestDTO {
    private Long pharmacyId;
    private Long patientId;
    private Long prescriptionId;   // nullable
    private LocalDate purchaseDate;
    private BigDecimal totalAmount;
}