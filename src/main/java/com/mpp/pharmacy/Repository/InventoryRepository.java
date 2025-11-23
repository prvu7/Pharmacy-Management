package com.mpp.pharmacy.Repository;


import com.mpp.pharmacy.Entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    List<Inventory> findByPharmacy_PharmacyId(Long pharmacyId);

    List<Inventory> findByDrug_DrugId(Long drugId);
}