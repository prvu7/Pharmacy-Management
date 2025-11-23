package com.mpp.pharmacy.ServiceInterface;

import com.mpp.pharmacy.DTO.PurchaseDTO;
import com.mpp.pharmacy.RequestDTO.PurchaseRequestDTO;

import java.util.List;

public interface PurchaseService {

    PurchaseDTO create(PurchaseRequestDTO request);

    PurchaseDTO getById(Long id);

    List<PurchaseDTO> getAll();

    List<PurchaseDTO> getByPatient(Long patientId);

    List<PurchaseDTO> getByPharmacy(Long pharmacyId);

    PurchaseDTO update(Long id, PurchaseRequestDTO request);

    void delete(Long id);
}