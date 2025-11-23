package com.mpp.pharmacy.Controllers;

import com.mpp.pharmacy.DTO.PharmacyDTO;
import com.mpp.pharmacy.RequestDTO.PharmacyRequestDTO;
import com.mpp.pharmacy.ServiceInterface.PharmacyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pharmacies")
@RequiredArgsConstructor
public class PharmacyController {

    private final PharmacyService service;

    @PostMapping
    public PharmacyDTO create(@RequestBody PharmacyRequestDTO request) {
        return service.create(request);
    }

    @GetMapping("/{id}")
    public PharmacyDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    public List<PharmacyDTO> getAll() {
        return service.getAll();
    }

    @PutMapping("/{id}")
    public PharmacyDTO update(@PathVariable Long id, @RequestBody PharmacyRequestDTO request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}