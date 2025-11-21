package com.mpp.pharmacy.Entity;

import java.io.Serializable;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class PrescriptionDetailId implements Serializable{
    private Long prescription;
    private Long drug;
}
