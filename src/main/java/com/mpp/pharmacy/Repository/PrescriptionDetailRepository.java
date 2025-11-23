package com.mpp.pharmacy.Repository;

import com.mpp.pharmacy.Entity.PrescriptionDetailId;
import com.mpp.pharmacy.Entity.Prescription_Detail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrescriptionDetailRepository extends JpaRepository<Prescription_Detail, PrescriptionDetailId> {

    List<Prescription_Detail> findByPrescription_PrescriptionId(Long prescriptionId);

    List<Prescription_Detail> findByDrug_DrugId(Long drugId);
}