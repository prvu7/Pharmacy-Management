package com.mpp.pharmacy.Controllers;

import com.mpp.pharmacy.DTO.InventoryDTO;
import com.mpp.pharmacy.DTO.PharmacyDTO;
import com.mpp.pharmacy.RequestDTO.InventoryRequestDTO;
import com.mpp.pharmacy.RequestDTO.PharmacyRequestDTO;
import com.mpp.pharmacy.ServiceInterface.InventoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping
    public InventoryDTO create(@RequestBody InventoryRequestDTO request) {
        return inventoryService.create(request);
    }

    @GetMapping
    public InventoryDTO getInventoryById(@PathVariable Long id) {
        return inventoryService.getById(id);
    }

    @GetMapping
    public List<InventoryDTO> getInventory() {
        return inventoryService.getAll();
    }

    @GetMapping
    public List<InventoryDTO> getInventoryByPharmacy(@PathVariable Long pharmacyId) {
        return inventoryService.getByPharmacy(pharmacyId);
    }

    @GetMapping
    public List<InventoryDTO> getInventoryByDrug(@PathVariable Long drugId) {
        return inventoryService.getByDrug(drugId);
    }

    @PutMapping("/{id}")
    public InventoryDTO update(@PathVariable Long id, @RequestBody InventoryRequestDTO request) {
        return inventoryService.update(id, request);
    }

    @DeleteMapping
    public void delete(@PathVariable Long id) {
        inventoryService.delete(id);
    }
}
