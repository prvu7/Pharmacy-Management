package com.mpp.pharmacy.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "drug")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Drug {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "drug_id")
    private Long drugId;

    @Column(name = "drug_name", nullable = false, length = 100)
    private String drugName;

    @Column(name = "generic_name", length = 100)
    private String genericName;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "dosage_form", length = 50)
    private String dosageForm;

    @Column(name = "manufacturer", length = 100)
    private String manufacturer;

    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;
}
