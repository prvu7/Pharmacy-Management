package com.mpp.pharmacy.Repository;

import com.mpp.pharmacy.Entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    List<Purchase> findByPatient_PersonId(Long patientId);

    List<Purchase> findByPharmacy_PharmacyId(Long pharmacyId);

    List<Purchase> findByPrescription_PrescriptionId(Long prescriptionId);
}