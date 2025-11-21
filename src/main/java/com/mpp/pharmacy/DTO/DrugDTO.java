package com.mpp.pharmacy.DTO;

import lombok.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DrugDTO {
    private Long drugId;
    private String drugName;
    private String genericName;
    private String description;
    private String dosageForm;
    private String manufacturer;
    private BigDecimal price;
}