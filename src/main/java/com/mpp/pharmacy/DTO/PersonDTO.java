package com.mpp.pharmacy.DTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonDTO {
    private Long personId;
    private String firstName;
    private String lastName;
    private String sex; // M, F, O
    private String dateOfBirth; // Use String or LocalDate
    private String phone;
    private String email;
    private String address;
    private String role; // patient, doctor, pharmacist
}

