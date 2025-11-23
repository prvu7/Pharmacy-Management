package com.mpp.pharmacy.RequestDTO;

import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryRequestDTO {
    private Long pharmacyId;
    private Long drugId;
    private Integer quantityInStock;
    private LocalDate expiryDate;
}