package com.mpp.pharmacy.ServiceInterface;

import com.mpp.pharmacy.DTO.InventoryDTO;
import com.mpp.pharmacy.RequestDTO.InventoryRequestDTO;

import java.util.List;

public interface InventoryService {

    InventoryDTO create(InventoryRequestDTO request);

    InventoryDTO getById(Long id);

    List<InventoryDTO> getAll();

    List<InventoryDTO> getByPharmacy(Long pharmacyId);

    List<InventoryDTO> getByDrug(Long drugId);

    InventoryDTO update(Long id, InventoryRequestDTO request);

    void delete(Long id);
}