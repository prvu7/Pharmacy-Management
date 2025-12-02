package com.mpp.pharmacy.Controllers;

import com.mpp.pharmacy.DTO.PharmacyDTO;
import com.mpp.pharmacy.RequestDTO.PharmacyRequestDTO;
import com.mpp.pharmacy.ServiceInterface.PharmacyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pharmacies")
@RequiredArgsConstructor
public class PharmacyController {

    private final PharmacyService service;

    @PostMapping
    public ResponseEntity<PharmacyDTO> create(@RequestBody PharmacyRequestDTO request) {
        PharmacyDTO pharmacy = service.create(request);
        return ResponseEntity.ok(pharmacy);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PharmacyDTO> getById(@PathVariable Long id) {
        PharmacyDTO pharmacy = service.getById(id);
        return ResponseEntity.ok(pharmacy);
    }

    @GetMapping
    public ResponseEntity<List<PharmacyDTO>> getAll() {
        List<PharmacyDTO> pharmacies = service.getAll();
        return ResponseEntity.ok(pharmacies);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PharmacyDTO> update(@PathVariable Long id, @RequestBody PharmacyRequestDTO request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}