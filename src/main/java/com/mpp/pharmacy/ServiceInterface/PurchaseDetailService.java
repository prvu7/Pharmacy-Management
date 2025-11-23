package com.mpp.pharmacy.ServiceInterface;

import com.mpp.pharmacy.DTO.PurchaseDetailDTO;
import com.mpp.pharmacy.RequestDTO.PurchaseDetailRequestDTO;

import java.util.List;

public interface PurchaseDetailService {

    PurchaseDetailDTO create(PurchaseDetailRequestDTO request);

    PurchaseDetailDTO getById(Long purchaseId, Long drugId);

    List<PurchaseDetailDTO> getAll();

    List<PurchaseDetailDTO> getByPurchase(Long purchaseId);

    List<PurchaseDetailDTO> getByDrug(Long drugId);

    PurchaseDetailDTO update(Long purchaseId, Long drugId, PurchaseDetailRequestDTO request);

    void delete(Long purchaseId, Long drugId);
}