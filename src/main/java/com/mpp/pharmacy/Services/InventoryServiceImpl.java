package com.mpp.pharmacy.Services;

import com.mpp.pharmacy.DTO.InventoryDTO;
import com.mpp.pharmacy.Domain.InventoryDomain;
import com.mpp.pharmacy.Entity.Inventory;
import com.mpp.pharmacy.Exception.InvalidRequestException;
import com.mpp.pharmacy.Mapper.InventoryMapper;
import com.mpp.pharmacy.Repository.InventoryRepository;
import com.mpp.pharmacy.RequestDTO.InventoryRequestDTO;
import com.mpp.pharmacy.ServiceInterface.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryMapper mapper;
    private final InventoryDomain inventoryDomain;

    @Override
    public InventoryDTO create(InventoryRequestDTO request) {
        log.info("Service: Creating inventory record");

        if (request == null) {
            throw new InvalidRequestException("Inventory request cannot be null");
        }

        Inventory created = inventoryDomain.create(request);
        return mapper.toDTO(created);
    }

    @Override
    public InventoryDTO getById(Long id) {
        log.debug("Service: Fetching inventory with id: {}", id);

        if (id == null) {
            throw new InvalidRequestException("Inventory ID cannot be null");
        }

        Inventory inventory = inventoryDomain.getById(id);
        return mapper.toDTO(inventory);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventoryDTO> getAll() {
        log.debug("Service: Fetching all inventory records");
        return inventoryRepository.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventoryDTO> getByPharmacy(Long pharmacyId) {
        log.debug("Service: Fetching inventory for pharmacy: {}", pharmacyId);

        if (pharmacyId == null) {
            throw new InvalidRequestException("Pharmacy ID cannot be null");
        }

        return inventoryRepository.findByPharmacy_PharmacyId(pharmacyId)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventoryDTO> getByDrug(Long drugId) {
        log.debug("Service: Fetching inventory for drug: {}", drugId);

        if (drugId == null) {
            throw new InvalidRequestException("Drug ID cannot be null");
        }

        return inventoryRepository.findByDrug_DrugId(drugId)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public InventoryDTO update(Long id, InventoryRequestDTO request) {
        log.info("Service: Updating inventory with id: {}", id);

        if (id == null) {
            throw new InvalidRequestException("Inventory ID cannot be null");
        }
        if (request == null) {
            throw new InvalidRequestException("Inventory request cannot be null");
        }

        Inventory updated = inventoryDomain.update(id, request);
        return mapper.toDTO(updated);
    }

    @Override
    public void delete(Long id) {
        log.info("Service: Deleting inventory with id: {}", id);

        if (id == null) {
            throw new InvalidRequestException("Inventory ID cannot be null");
        }

        inventoryDomain.delete(id);
    }
}