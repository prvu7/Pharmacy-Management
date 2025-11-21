package com.mpp.pharmacy.Entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "treatment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Treatment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "treatment_id")
    private Long treatmentId;

    @Column(name = "treatment_name", nullable = false, length = 100)
    private String treatmentName;

    @Column(columnDefinition = "TEXT")
    private String description;

    // doctor_id --> person.person_id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "doctor_id",
            referencedColumnName = "person_id",
            foreignKey = @ForeignKey(name = "fk_treatment_doctor")
    )
    private Person doctor;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;
}
