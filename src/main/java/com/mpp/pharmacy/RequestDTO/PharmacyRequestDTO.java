package com.mpp.pharmacy.RequestDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PharmacyRequestDTO {
    @NotBlank(message = "Pharmacy name is required")
    @Size(min = 3, max = 100, message = "Pharmacy name must be between 3 and 100 characters")
    @Pattern(regexp = "^[a-zA-Z0-9\\s.,'&-]+$",
            message = "Pharmacy name can only contain letters, numbers, spaces, and basic punctuation (.,'-&)")
    private String name;

    @NotBlank(message = "Address is required")
    @Size(min = 10, max = 200, message = "Address must be between 10 and 200 characters")
    private String address;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[+]?[0-9]{10,15}$",
            message = "Invalid phone format. Must be 10-15 digits, optionally starting with '+'")
    private String phone;
}