package com.mpp.pharmacy.RequestDTO;

import com.mpp.pharmacy.Enum.Role;
import com.mpp.pharmacy.Enum.Sex;
import lombok.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonRequestDTO {
    private String firstName;
    private String lastName;
    private Sex sex;
    private LocalDate dateOfBirth;
    private String phone;
    private String email;
    private String address;
    private Role role;
}
