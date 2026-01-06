package com.mpp.pharmacy.Controllers;

import com.mpp.pharmacy.DTO.InventoryDTO;
import com.mpp.pharmacy.DTO.PharmacyDTO;
import com.mpp.pharmacy.RequestDTO.InventoryRequestDTO;
import com.mpp.pharmacy.RequestDTO.PharmacyRequestDTO;
import com.mpp.pharmacy.ServiceInterface.InventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping
    public ResponseEntity<InventoryDTO> create(@RequestBody InventoryRequestDTO request) {
        InventoryDTO inventory = inventoryService.create(request);
        return ResponseEntity.ok().body(inventory);
    }

    @GetMapping
    public ResponseEntity<InventoryDTO> getInventoryById(@PathVariable Long id) {
        InventoryDTO inventory = inventoryService.getById(id);
        return ResponseEntity.ok().body(inventory);
    }

    @GetMapping
    public ResponseEntity<List<InventoryDTO>> getInventory() {
        List<InventoryDTO> inventory = inventoryService.getAll();
        return ResponseEntity.ok().body(inventory);
    }

    @GetMapping
    public ResponseEntity<List<InventoryDTO>> getInventoryByPharmacy(@PathVariable Long pharmacyId) {
        List<InventoryDTO> inventory = inventoryService.getByPharmacy(pharmacyId);
        return ResponseEntity.ok().body(inventory);
    }

    @GetMapping
    public ResponseEntity<List<InventoryDTO>> getInventoryByDrug(@PathVariable Long drugId) {
        List<InventoryDTO> inventory = inventoryService.getByDrug(drugId);
        return ResponseEntity.ok().body(inventory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventoryDTO> update(@PathVariable Long id, @RequestBody InventoryRequestDTO request) {
        return ResponseEntity.ok(inventoryService.update(id, request));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        inventoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
