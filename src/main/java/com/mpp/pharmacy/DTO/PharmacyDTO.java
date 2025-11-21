package com.mpp.pharmacy.DTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PharmacyDTO {
    private Long pharmacyId;
    private String name;
    private String address;
    private String phone;
}