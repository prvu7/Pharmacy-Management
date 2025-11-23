package com.mpp.pharmacy.Repository;

import com.mpp.pharmacy.Entity.PurchaseDetailId;
import com.mpp.pharmacy.Entity.Purchase_Detail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseDetailRepository extends JpaRepository<Purchase_Detail, PurchaseDetailId> {

    List<Purchase_Detail> findByPurchase_PurchaseId(Long purchaseId);

    List<Purchase_Detail> findByDrug_DrugId(Long drugId);
}