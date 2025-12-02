package com.mpp.pharmacy.Services;

import com.mpp.pharmacy.DTO.InventoryDTO;
import com.mpp.pharmacy.Entity.Drug;
import com.mpp.pharmacy.Entity.Inventory;
import com.mpp.pharmacy.Entity.Pharmacy;
import com.mpp.pharmacy.Exception.ResourceNotFoundException;
import com.mpp.pharmacy.Loggers.CustomLogger;
import com.mpp.pharmacy.Loggers.LogType;
import com.mpp.pharmacy.Mapper.InventoryMapper;
import com.mpp.pharmacy.Repository.DrugRepository;
import com.mpp.pharmacy.Repository.InventoryRepository;
import com.mpp.pharmacy.Repository.PharmacyRepository;
import com.mpp.pharmacy.RequestDTO.InventoryRequestDTO;
import com.mpp.pharmacy.ServiceInterface.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final PharmacyRepository pharmacyRepository;
    private final DrugRepository drugRepository;
    private final InventoryMapper mapper;
    private final CustomLogger logger = CustomLogger.getInstance();

    @Override
    public InventoryDTO create(InventoryRequestDTO request) {
        Pharmacy pharmacy = pharmacyRepository.findById(request.getPharmacyId())
                .orElseThrow(() -> new ResourceNotFoundException("Pharmacy not found"));

        Drug drug = drugRepository.findById(request.getDrugId())
                .orElseThrow(() -> new ResourceNotFoundException("Drug not found"));

        Inventory inventory = mapper.fromRequest(request);
        inventory.setPharmacy(pharmacy);
        inventory.setDrug(drug);

        return mapper.toDTO(inventoryRepository.save(inventory));
    }

    @Override
    public InventoryDTO getById(Long id) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found"));
        return mapper.toDTO(inventory);
    }

    @Override
    public List<InventoryDTO> getAll() {
        return inventoryRepository.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public List<InventoryDTO> getByPharmacy(Long pharmacyId) {
        return inventoryRepository.findByPharmacy_PharmacyId(pharmacyId)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public List<InventoryDTO> getByDrug(Long drugId) {
        return inventoryRepository.findByDrug_DrugId(drugId)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public InventoryDTO update(Long id, InventoryRequestDTO request) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found"));

        Pharmacy pharmacy = pharmacyRepository.findById(request.getPharmacyId())
                .orElseThrow(() -> new ResourceNotFoundException("Pharmacy not found"));

        Drug drug = drugRepository.findById(request.getDrugId())
                .orElseThrow(() -> new ResourceNotFoundException("Drug not found"));

        inventory.setPharmacy(pharmacy);
        inventory.setDrug(drug);
        inventory.setQuantityInStock(request.getQuantityInStock());
        inventory.setExpiryDate(request.getExpiryDate());

        return mapper.toDTO(inventoryRepository.save(inventory));
    }

    @Override
    public void delete(Long id) {
        if (!inventoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Inventory not found");
        }
        inventoryRepository.deleteById(id);
    }
}