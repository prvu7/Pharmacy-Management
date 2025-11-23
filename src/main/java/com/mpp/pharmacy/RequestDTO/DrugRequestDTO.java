package com.mpp.pharmacy.RequestDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DrugRequestDTO {
    private String drugName;
    private String genericName;
    private String description;
    private String dosageForm;
    private String manufacturer;
    private BigDecimal price;
}
