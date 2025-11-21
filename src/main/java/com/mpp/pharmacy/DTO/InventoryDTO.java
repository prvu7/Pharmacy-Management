package com.mpp.pharmacy.DTO;

import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryDTO {
    private Long inventoryId;
    private Long pharmacyId;
    private Long drugId;
    private Integer quantityInStock;
    private LocalDate expiryDate;
}