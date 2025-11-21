package com.mpp.pharmacy.Entity;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "purchase_detail")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(PurchaseDetailId.class)

public class Purchase_Detail {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_purchasedetail_purchase"))
    private Purchase purchase;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drug_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_purchasedetail_drug"))
    private Drug drug;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "unit_price", precision = 10, scale = 2)
    private BigDecimal unitPrice;
}
