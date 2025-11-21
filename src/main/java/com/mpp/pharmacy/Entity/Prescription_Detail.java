package com.mpp.pharmacy.Entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "prescription_detail")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(PrescriptionDetailId.class)

public class Prescription_Detail{
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prescription_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_prescriptiondetail_prescription"))
    private Prescription prescription;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drug_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_prescriptiondetail_drug"))
    private Drug drug;

    @Column(length = 50)
    private String dosage;

    @Column(name = "duration_days")
    private Integer durationDays;

    private Integer quantity;
}
