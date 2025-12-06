package com.mpp.pharmacy.Repository;


import com.mpp.pharmacy.Entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    List<Inventory> findByDrug_DrugId(Long drugId);
    List<Inventory> findByPharmacy_PharmacyId(Long pharmacyId);
    Optional<Inventory> findByPharmacy_PharmacyIdAndDrug_DrugId(Long pharmacyId, Long drugId);
    List<Inventory> findByExpiryDateBefore(LocalDate date);
    List<Inventory> findByQuantityInStockLessThan(Integer threshold);

    @Query("SELECT i FROM Inventory i WHERE i.expiryDate BETWEEN :startDate AND :endDate")
    List<Inventory> findByExpiryDateBetween(LocalDate startDate, LocalDate endDate);
}