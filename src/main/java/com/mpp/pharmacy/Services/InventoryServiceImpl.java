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
import com.mpp.pharmacy.Domain.InventoryDomain;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryServiceImpl implements InventoryService {

    private final InventoryDomain domain;
    private final InventoryMapper mapper;

    @Override
    public InventoryDTO create(InventoryRequestDTO request) {
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }

        Inventory inventory = domain.create(request);

        return mapper.toDTO(inventory);
    }

    @Override
    public InventoryDTO getById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }

        Inventory inventory = domain.getById(id);

        return mapper.toDTO(inventory);
    }

    @Override
    public List<InventoryDTO> getAll() {
        return domain.getAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public List<InventoryDTO> getByPharmacy(Long pharmacyId) {
        if (pharmacyId == null) {
            throw new IllegalArgumentException("Pharmacy ID cannot be null");
        }

        return domain.getByPharmacy(pharmacyId).stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public List<InventoryDTO> getByDrug(Long drugId) {
        if (drugId == null) {
            throw new IllegalArgumentException("Drug ID cannot be null");
        }

        return domain.getByDrug(drugId).stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public InventoryDTO update(Long id, InventoryRequestDTO request) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }

        Inventory updatedInventory = domain.update(id, request);

        return mapper.toDTO(updatedInventory);
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }

        domain.delete(id);
    }
}