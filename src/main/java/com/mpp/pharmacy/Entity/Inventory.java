package com.mpp.pharmacy.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "inventory")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_id")
    private Long inventoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pharmacy_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_inventory_pharmacy"))
    private Pharmacy pharmacy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drug_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_inventory_drug"))
    private Drug drug;

    @Column(name = "quantity_in_stock")
    private Integer quantityInStock;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;
}
