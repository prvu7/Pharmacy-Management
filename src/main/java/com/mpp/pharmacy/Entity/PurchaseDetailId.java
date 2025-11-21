package com.mpp.pharmacy.Entity;

import java.io.Serializable;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class PurchaseDetailId implements Serializable {
    private Long purchase;
    private Long drug;
}
