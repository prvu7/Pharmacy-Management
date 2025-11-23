package com.mpp.pharmacy.RequestDTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PharmacyRequestDTO {
    private String name;
    private String address;
    private String phone;
}