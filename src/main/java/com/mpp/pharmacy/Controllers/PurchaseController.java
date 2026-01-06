package com.mpp.pharmacy.Controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import org.springframework.http.ResponseEntity;
import com.mpp.pharmacy.ServiceInterface.PurchaseService;
import com.mpp.pharmacy.RequestDTO.PurchaseRequestDTO;
import com.mpp.pharmacy.DTO.PurchaseDTO;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequestMapping("/api/purchases")
public class PurchaseController {

    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping()
    public ResponseEntity<PurchaseDTO> createPurchase(@RequestBody PurchaseRequestDTO request) {

        PurchaseDTO purchaseDTO = purchaseService.create(request);
        return ResponseEntity.created(null).body(purchaseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseDTO> getPurchaseById(@PathVariable Long id) {
        PurchaseDTO purchaseDTO = purchaseService.getById(id);
        return ResponseEntity.ok(purchaseDTO);
    }

    @GetMapping()
    public ResponseEntity<List<PurchaseDTO>> getAllPurchases() {
        return ResponseEntity.ok(purchaseService.getAll());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<PurchaseDTO> updatePurchase(@PathVariable Long id, @RequestBody PurchaseRequestDTO request) {
        PurchaseDTO updatedPurchase = purchaseService.update(id, request);
        return ResponseEntity.ok(updatedPurchase);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePurchase(@PathVariable Long id) {
        purchaseService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // -------------------------------------------------------
    // Additional Endpoints
    // -------------------------------------------------------

    @GetMapping("/by-patient/{patientId}")
    public ResponseEntity<List<PurchaseDTO>> getPurchasesByPatient(@PathVariable Long patientId) {
        List<PurchaseDTO> purchases = purchaseService.getByPatient(patientId);
        return ResponseEntity.ok(purchases);
    }

    @GetMapping("/by-pharmacy/{pharmacyId}")
    public ResponseEntity<List<PurchaseDTO>> getPurchasesByPharmacy(@PathVariable Long pharmacyId) {
        List<PurchaseDTO> purchases = purchaseService.getByPharmacy(pharmacyId);
        return ResponseEntity.ok(purchases);
    }
}
