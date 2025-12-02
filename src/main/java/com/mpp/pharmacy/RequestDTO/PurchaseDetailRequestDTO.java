package com.mpp.pharmacy.RequestDTO;


import lombok.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseDetailRequestDTO {
    private Long purchaseId;
    private Long drugId;
    private Integer quantity;
    private BigDecimal unitPrice;
}
