package com.mpp.pharmacy.DTO;

import lombok.*;
import java.time.LocalDate;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseDTO {
    private Long purchaseId;
    private Long pharmacyId;
    private Long patientId;
    private Long prescriptionId;
    private LocalDate purchaseDate;
    private BigDecimal totalAmount;
    private List<PurchaseDetailDTO> details;
}