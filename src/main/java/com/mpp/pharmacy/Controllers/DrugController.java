package com.mpp.pharmacy.Controllers;

import com.mpp.pharmacy.DTO.DrugDTO;
import com.mpp.pharmacy.RequestDTO.DrugRequestDTO;
import com.mpp.pharmacy.ServiceInterface.DrugService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drugs")
@RequiredArgsConstructor
public class DrugController {

    private final DrugService drugService;

    @PostMapping
    public DrugDTO createDrug(@RequestBody DrugRequestDTO request) {
        return drugService.create(request);
    }

    @GetMapping
    public List<DrugDTO> getAllDrugs() {
        return drugService.getAll();
    }

    @GetMapping("/{id}")
    public DrugDTO getDrugById(@PathVariable Long id) {
        return drugService.getById(id);
    }

    @PutMapping("/{id}")
    public DrugDTO updateDrug(@PathVariable Long id, @RequestBody DrugRequestDTO request) {
        return drugService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteDrug(@PathVariable Long id) {
        drugService.delete(id);
    }
}