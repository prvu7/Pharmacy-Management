package com.mpp.pharmacy.DTO;
import lombok.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseDetailDTO {
    private Long purchaseId;
    private Long drugId;
    private Integer quantity;
    private BigDecimal unitPrice;
}